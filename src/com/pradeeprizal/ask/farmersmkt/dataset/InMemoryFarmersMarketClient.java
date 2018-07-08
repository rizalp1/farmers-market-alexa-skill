package com.pradeeprizal.ask.farmersmkt.dataset;

import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryFarmersMarketClient implements FarmersMarketDatasetClient, AutoCloseable {
    private static Logger LOGGER = LoggerFactory.getLogger(InMemoryFarmersMarketClient.class);
    private static final String MARKET_FIELD = "market";
    private static final String INDEX_FIELD = "index";

    private final IndexSearcher mSearcher;
    private final List<FarmersMarketRecord> mRecords;

    /**
     * Creates an instance of InMemoryFarmersMarketClient.
     * @param records List of farmers market to index in memory.
     */
    public InMemoryFarmersMarketClient(final List<FarmersMarketRecord> records) {
        mRecords = records;
        mSearcher = buildSearchIndex(records);
    }

    @Override
    public Optional<FarmersMarketRecord> query(String queryString) {
        try {
            Analyzer analyzer = new StandardAnalyzer();
            QueryParser parser = new QueryParser(MARKET_FIELD, analyzer);
            Query query = parser.parse(queryString);

            TopDocs topDocs = mSearcher.search(query, 1);
            if (topDocs.totalHits == 0) {
                return Optional.empty();
            }

            int topIndex = topDocs.scoreDocs[0].doc;
            return Optional.of(mRecords.get(topIndex));

        } catch (IOException | ParseException e) {
            LOGGER.error("Exception while searching in lucene for : " + queryString, e);
            return Optional.empty();
        }
    }

    private IndexSearcher buildSearchIndex(final List<FarmersMarketRecord> records) {
        Analyzer analyzer = new StandardAnalyzer();
        Directory index = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try {
            IndexWriter writer = new IndexWriter(index, config);
            for (int i = 0; i <= records.size() - 1; i++) {
                Document doc = new Document();
                doc.add(new TextField(MARKET_FIELD, records.get(i).getMarket(), Field.Store.NO));
                doc.add(new StoredField(INDEX_FIELD, i));
                writer.addDocument(doc);
            }

            writer.commit();
            writer.close();

            IndexReader reader = DirectoryReader.open(index);
            return new IndexSearcher(reader);

        } catch (IOException e) {
            LOGGER.error("IOException while building a lucene index: ");
            throw new RuntimeException("Could not build lucene index", e);
        }
    }

    @Override
    public void close() throws Exception {
        if (mSearcher != null && mSearcher.getIndexReader() != null) {
            mSearcher.getIndexReader().close();
        }
    }
}
