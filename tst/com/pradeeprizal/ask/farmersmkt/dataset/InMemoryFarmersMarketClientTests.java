package com.pradeeprizal.ask.farmersmkt.dataset;

import com.pradeeprizal.ask.farmersmkt.dataset.model.FarmersMarketRecord;
import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Tests the in memory client.
 */
public class InMemoryFarmersMarketClientTests {
    private List<FarmersMarketRecord> mRecords =
            Arrays.asList(
                    FarmersMarketRecord.builder().market("Ballard").days(Arrays.asList("Sunday", "Monday")).build(),
                    FarmersMarketRecord.builder().market("Capitol").days(Arrays.asList("Sunday", "Tuesday")).build(),
                    FarmersMarketRecord.builder().market("Pike").days(Arrays.asList("Sunday")).build()
            );

    private InMemoryFarmersMarketClient mClient;
    @BeforeTest
    public void setUp() {
        mClient = new InMemoryFarmersMarketClient(mRecords);
    }

    @Test(dataProvider = "ExistsDataProvider")
    public void testQueryExists(final String query) {
        Optional<FarmersMarketRecord> result = mClient.query(query);
        Assert.assertTrue(result.isPresent());
    }

    @Test(dataProvider = "NotExistsDataProvider")
    public void testQueryNotExists(final String query) {
        Optional<FarmersMarketRecord> result = mClient.query(query);
        Assert.assertFalse(result.isPresent());
    }

    @DataProvider(name = "ExistsDataProvider")
    public Object[][] dataProviderExists() {
        return new Object[][] {
                {"Ballard"},
                {"Ballard Farmers Market"},
                {"Capitol Hill"},
                {"Pike Place"},
                {"Pike"},
                {"Downtown"},
                {"Pike Place Market"},
                {"Ballard Farmers Market"},
                {"Ballard Market"}
        };
    }

    @DataProvider(name = "NotExistsDataProvider")
    public Object[][] dataProviderNotExists() {
        return new Object[][]{
                {"Bainbridge"},
                {"Ballard Market Farmers"}
        };
    }
}
