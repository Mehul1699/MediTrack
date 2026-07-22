package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.AppointmentObserver;

import java.time.LocalDateTime;

public class BillingObserver implements AppointmentObserver {
    @Override
    public void update(Appointment appointment) {
        System.out.println(
                "Notification for Billing. An appointment (ID: " + appointment.getId() + ") "
                        + "has been updated to " + appointment.getStatus()
                        + ". Scheduled for: "
                        + LocalDateTime.of(appointment.getDate(), appointment.getTime())
        );
    }
}
