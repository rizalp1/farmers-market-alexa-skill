package com.pradeeprizal.ask.farmersmkt.dataset.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder(toBuilder = true)
@Data
public class FarmersMarketRecord {
    private String market;
    private List<String> days;
    private String startTime;
    private String endTime;
    private String startDate;
    private String endDate;
    private String address;
}
