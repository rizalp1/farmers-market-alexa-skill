package com.pradeeprizal.ask.farmersmkt.dataset;

import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;

import java.util.List;

/**
 * Loads dataset for FarmersMarketRecords.
 */
public interface DatasetLoader {
    /** Loads the farmers market dataset into a list. **/
    List<FarmersMarketRecord> load();
}
