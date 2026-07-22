package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.exception.InvalidDataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class DateValidator {

    public static void validateDate(LocalDate date, LocalTime time) {
        if (Objects.isNull(date)) {
            throw new InvalidDataException("Appointment Date can't be null");
        }
        if (Objects.isNull(time)) {
            throw new InvalidDataException("Appointment Time can't be null");
        }
    }

    public static void validateAppointmentDate(LocalDate date, LocalTime time) {
        validateDate(date, time);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidDataException(
                    "Appointment can't be scheduled in the past"
            );
        }
        if (!Constants.TIME_SLOTS.contains(time)) {
            throw new InvalidDataException(
                    "Invalid appointment time slot"
            );
        }
    }

}
