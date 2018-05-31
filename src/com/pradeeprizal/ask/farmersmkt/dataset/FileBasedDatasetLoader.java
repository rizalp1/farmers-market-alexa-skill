package com.pradeeprizal.ask.farmersmkt.dataset;

import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;

import java.util.Arrays;
import java.util.List;

/**
 * Loads dataset from a jsonl file.
 */
public class FileBasedDatasetLoader implements DatasetLoader {

    @Override
    public List<FarmersMarketRecord> load() {
        // TODO: Unmock this
        // Let's mock this for now.

        return Arrays.asList(
                FarmersMarketRecord.builder()
                    .market("Ballard")
                    .address("123 Ballard Way")
                    .startTime("2:00 PM")
                    .endTime("4:00 PM")
                    .days(Arrays.asList("Sunday"))
                    .build(),
                FarmersMarketRecord.builder()
                        .market("Capitol Hill")
                        .address("123 Cap Way")
                        .startTime("1:00 PM")
                        .endTime("3:00 PM")
                        .days(Arrays.asList("Saturday", "Monday", "Wednesday"))
                        .build(),
                FarmersMarketRecord.builder()
                        .market("Downtown")
                        .address("123 Downtown Way")
                        .startTime("2:00 PM")
                        .endTime("4:00 PM")
                        .days(Arrays.asList("Wednesday"))
                        .build(),
                FarmersMarketRecord.builder()
                        .market("Magnolia")
                        .address("123 Magnolia Way")
                        .startTime("1:00 PM")
                        .endTime("6:00 PM")
                        .days(Arrays.asList("Wednesday"))
                        .build()
        );
    }
}
