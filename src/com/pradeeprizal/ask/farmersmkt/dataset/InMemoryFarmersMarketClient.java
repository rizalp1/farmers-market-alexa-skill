package com.pradeeprizal.ask.farmersmkt.dataset;

import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryFarmersMarketClient implements FarmersMarketDatasetClient {
    private static Logger LOGGER = LoggerFactory.getLogger(InMemoryFarmersMarketClient.class);
    private static final String MARKET_FIELD = "market";
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
            Query query = new TermQuery(new Term(MARKET_FIELD, queryString));
            TopDocs topDocs = mSearcher.search(query, 1);
            if (topDocs.totalHits == 0) {
                return Optional.empty();
            }

            int topIndex = topDocs.scoreDocs[0].doc;
            return Optional.of(mRecords.get(topIndex));

        } catch (IOException e) {
            LOGGER.error("IOException while searching in lucene for : " + queryString, e);
            return Optional.empty();
        }
    }

    private IndexSearcher buildSearchIndex(final List<FarmersMarketRecord> records) {
        try {
            StandardAnalyzer analyzer = new StandardAnalyzer();
            Directory index = new RAMDirectory();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter writer = new IndexWriter(index, config);

            writer.addDocuments(
                    records.stream().map(record -> {
                        Document doc = new Document();
                        doc.add(new TextField(MARKET_FIELD, record.getMarket(), Field.Store.NO));
                        return doc;
                    }).collect(Collectors.toList())
            );
            writer.commit();
            writer.close();

            IndexReader reader = DirectoryReader.open(index);
            return new IndexSearcher(reader);

        } catch (IOException e) {
            throw new RuntimeException("Could not build lucene index", e);
        }
    }
}
