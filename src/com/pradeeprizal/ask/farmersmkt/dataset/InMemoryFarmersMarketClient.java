package com.pradeeprizal.ask.farmersmkt.dataset;

import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InMemoryFarmersMarketClient implements FarmersMarketDatasetClient {

    /**
     * Creates an instance of InMemoryFarmersMarketClient.
     * @param records List of farmers market to index in memory.
     */
    public InMemoryFarmersMarketClient(final List<FarmersMarketRecord> records) {
        // TODO: Index this using some library or build custom one.
    }

    @Override
    public Optional<FarmersMarketRecord> query(String queryString) {
        // TODO: Replace with actual call and in-memory search.
        return Optional.of(
                FarmersMarketRecord.builder()
                    .market("Ballard Farmers Market")
                    .days(Collections.singletonList("Sunday"))
                    .startTime("9:00AM")
                    .endTime("3:00PM")
                    .startDate("1/1")
                    .endDate("12/31")
                    .address("Ballard Way")
                .build()
        );
    }
}
