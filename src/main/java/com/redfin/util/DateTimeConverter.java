package com.redfin.util;

import com.google.common.collect.ImmutableMap;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static java.time.DayOfWeek.*;

public class DateTimeConverter {

    private static final ZoneId ZONE = ZoneId.of("America/Los_Angeles");
    private static final String TIME_FORMAT = "HH:mm";
    private static final Map<DayOfWeek, Integer> dayOfWeekNumber = ImmutableMap.<DayOfWeek, Integer>builder()
            .put(SUNDAY, 0)
            .put(MONDAY, 1)
            .put(TUESDAY, 2)
            .put(WEDNESDAY, 3)
            .put(THURSDAY, 4)
            .put(FRIDAY, 5)
            .put(SATURDAY, 6)
            .build();

    public static ZonedDateTime convert(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalTime localStartTime = formatter.parse(time, LocalTime::from)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        return Instant.now().atZone(ZONE).with(localStartTime);
    }

    public static int dayOfWeekNumber() {
        return dayOfWeekNumber.get(dayOfWeek());
    }

    public static DayOfWeek dayOfWeek() {
        return nowAtZone().getDayOfWeek();
    }

    public static ZonedDateTime nowAtZone() {
        return Instant.now().atZone(ZONE);
    }
}
