package com.pradeeprizal.ask.farmersmkt.dataset;

import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;

import java.util.Arrays;
import java.util.List;

/**
 * Loads dataset from a jsonl file. For V0 (and performance), this loads from a static variable.
 */
public class FileBasedDatasetLoader implements DatasetLoader {

    @Override
    public List<FarmersMarketRecord> load() {
        return PugetSoundData.FARMERS_MKT_RECORDS;
    }
}
