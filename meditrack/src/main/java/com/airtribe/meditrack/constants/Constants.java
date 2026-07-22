package com.airtribe.meditrack.constants;

import java.time.LocalTime;
import java.util.List;

public class Constants {

    public static final List<LocalTime> TIME_SLOTS = List.of(
            LocalTime.of(9, 0),
            LocalTime.of(9, 30),
            LocalTime.of(10, 0),
            LocalTime.of(10, 30),
            LocalTime.of(11, 0),
            LocalTime.of(11, 30),
            LocalTime.of(14, 0),
            LocalTime.of(14, 30),
            LocalTime.of(15, 0),
            LocalTime.of(15, 30),
            LocalTime.of(16, 0),
            LocalTime.of(16, 30)
    );
    public static double TAX_RATE = 0.10;
    public static double DISCOUNT = 0.20;

}
