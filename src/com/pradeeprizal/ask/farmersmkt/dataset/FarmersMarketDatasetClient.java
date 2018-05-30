package com.pradeeprizal.ask.farmersmkt.dataset;

import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;

import java.util.Optional;

/**
 * Created by pradeeprizal on 5/29/18.
 */
public interface FarmersMarketDatasetClient {
    /**
     * Queries the underlying dataset and returns a farmers market record match if one exists.
     * @param queryString market name to query for. e.g. Ballard Farmers Market.
     * @return FarmersMarketRecord if one exists.
     */
    Optional<FarmersMarketRecord> query(final String queryString);
}
