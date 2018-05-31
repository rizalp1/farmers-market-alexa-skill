package com.pradeeprizal.ask.farmersmkt.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Has methods to convert 'timeslot' entries to a certain day string (sunday -> saturday)
 */
public class TimeSlotDayUtils {
    /** Listed in ISO 8601 Standard 1-7. **/
    final static private List<String> DaysOfWeek = Arrays.asList("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday");

    final static private Set<String> DaysOfWeekSet = new HashSet<>(DaysOfWeek);

    /**
     * Get the day associated with this timeslot. Timeslot can not be null.
     * @param timeSlot can not be null. Will throw a NPE.
     * @return Day associated with this timeslot (monday - sunday)
     */
    public static String getDay(final String timeSlot) {
        LocalDate localDateToday = LocalDate.now(ZoneId.of("America/Los_Angeles"));
        String timeSlotToLower = timeSlot.toLowerCase();

        if (DaysOfWeekSet.contains(timeSlotToLower)) {
            return timeSlotToLower;
        } else if ("tomorrow".equals(timeSlotToLower)) {
            return DaysOfWeek.get(localDateToday.getDayOfWeek().getValue());
        } else {
            // If user said "today" or "now" or we can't understand what they said,
            //      we default to today.
            return DaysOfWeek.get(localDateToday.getDayOfWeek().getValue() - 1);
        }
    }
}
