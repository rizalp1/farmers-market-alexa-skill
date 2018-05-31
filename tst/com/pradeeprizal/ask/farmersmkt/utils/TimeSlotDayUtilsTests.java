package com.pradeeprizal.ask.farmersmkt.utils;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

public class TimeSlotDayUtilsTests {
    final static private List<String> DaysOfWeek = Arrays.asList("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday");

    @Test(dataProvider = "timeSlotData")
    public void testTimeSlot(String input, String expected) {
        String actual = TimeSlotDayUtils.getDay(input);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "timeSlotData")
    public static Object[][] timeSlotData() {
        LocalDate localDateToday = LocalDate.now(ZoneId.of("America/Los_Angeles"));

        return new Object[][] {
                { "sunday", "sunday"},
                { "monday", "monday"},
                { "tuesday", "tuesday"},
                { "wednesday", "wednesday"},
                { "thursday", "thursday"},
                { "friday", "friday"},
                { "saturday", "saturday"},
                { "today", DaysOfWeek.get(localDateToday.getDayOfWeek().getValue() - 1)},
                { "now", DaysOfWeek.get(localDateToday.getDayOfWeek().getValue() - 1)},
                { "tomorrow", DaysOfWeek.get(localDateToday.getDayOfWeek().getValue())},
                { "cannotUnderstand", DaysOfWeek.get(localDateToday.getDayOfWeek().getValue() - 1)},
        };
    }
}
