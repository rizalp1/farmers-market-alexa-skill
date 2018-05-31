package com.pradeeprizal.ask.farmersmkt.dataset;

import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;

import java.util.*;

/**
 * Groups farmers markets by days they are open, and has method to return random markets that open a certain day.
 */
public class FarmersMarketDayGrouper {
    final private Map<String, List<String>> mDayOpenToMarketMap;

    public FarmersMarketDayGrouper(final List<FarmersMarketRecord> allRecords) {
        mDayOpenToMarketMap = dayOpenToMarketNameMap(allRecords);
    }

    public List<String> getRandomMarketsForDay(String dayOpen, int returnCount) {
        List<String> marketsOpen = mDayOpenToMarketMap.get(dayOpen.toLowerCase());
        Collections.shuffle(marketsOpen);

        return (returnCount >= marketsOpen.size() ? marketsOpen : marketsOpen.subList(0, returnCount - 1));

    }

    /** package private **/
    Map<String, List<String>> dayOpenToMarketNameMap(final List<FarmersMarketRecord> records) {
        Map<String, List<String>> dayOpenToMarketMap = new HashMap<>();
        Arrays.asList(
                "sunday",
                "monday",
                "tuesday",
                "wednesday",
                "thursday",
                "friday",
                "saturday"
            ).forEach(
                    day -> dayOpenToMarketMap.put(day, new ArrayList<>())
            );

        for (FarmersMarketRecord record : records) {
            record.getDays().forEach(
                    day -> dayOpenToMarketMap.get(day.toLowerCase()).add(record.getMarket())
            );
        }

        return dayOpenToMarketMap;
    }
}
