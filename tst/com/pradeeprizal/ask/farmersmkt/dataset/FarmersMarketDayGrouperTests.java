package com.pradeeprizal.ask.farmersmkt.dataset;

import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class FarmersMarketDayGrouperTests {
    @Test
    public void testDayOpenToMarketMapper() {
        List<FarmersMarketRecord> input = Arrays.asList(
                FarmersMarketRecord.builder().market("Ballard").days(Arrays.asList("Sunday", "Monday")).build(),
                FarmersMarketRecord.builder().market("Capitol").days(Arrays.asList("Monday", "Tuesday")).build(),
                FarmersMarketRecord.builder().market("Downtown").days(Arrays.asList("Wednesday")).build()
        );
        Map<String, List<String>> output = new FarmersMarketDayGrouper(input).dayOpenToMarketNameMap(input);
        Map<String, List<String>> expected = new HashMap<>();
        expected.put("sunday", Arrays.asList("Ballard"));
        expected.put("monday", Arrays.asList("Ballard", "Capitol"));
        expected.put("tuesday", Arrays.asList("Capitol"));
        expected.put("wednesday", Arrays.asList("Downtown"));
        expected.put("thursday", Collections.emptyList());
        expected.put("friday", Collections.emptyList());
        expected.put("saturday", Collections.emptyList());
        Assert.assertEquals(output, expected, "Actual map is not equal to output map");
    }

    @Test
    public void testGetRandomFew() {
        List<FarmersMarketRecord> input = Arrays.asList(
                FarmersMarketRecord.builder().market("Ballard").days(Arrays.asList("Sunday", "Monday")).build(),
                FarmersMarketRecord.builder().market("Capitol").days(Arrays.asList("Sunday", "Tuesday")).build(),
                FarmersMarketRecord.builder().market("Downtown").days(Arrays.asList("Sunday")).build(),
                FarmersMarketRecord.builder().market("Other Location").days(Arrays.asList("Wednesday")).build()
        );
        List<String> output = new FarmersMarketDayGrouper(input).getRandomMarketsForDay("Sunday", 2);
        /** Make sure output contains only the list of markets that run on Sunday. **/
        Assert.assertTrue(! output.contains("Other Location"));
    }

    @Test
    public void testGetRandomAll() {
        List<FarmersMarketRecord> input = Arrays.asList(
                FarmersMarketRecord.builder().market("Ballard").days(Arrays.asList("Sunday", "Monday")).build(),
                FarmersMarketRecord.builder().market("Capitol").days(Arrays.asList("Sunday", "Tuesday")).build(),
                FarmersMarketRecord.builder().market("Downtown").days(Arrays.asList("Sunday")).build(),
                FarmersMarketRecord.builder().market("Other Location").days(Arrays.asList("Wednesday")).build()
        );
        List<String> output = new FarmersMarketDayGrouper(input).getRandomMarketsForDay("Sunday", 3);
        List<String> expected = Arrays.asList("Ballard", "Capitol", "Downtown");
        /** Make sure output contains only the list of markets that run on Sunday. **/
        Assert.assertEquals(new HashSet<String>(output), new HashSet<String>(expected));
    }
}
