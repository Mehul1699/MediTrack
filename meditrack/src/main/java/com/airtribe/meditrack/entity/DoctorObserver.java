package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.AppointmentObserver;

import java.time.LocalDateTime;

public class DoctorObserver implements AppointmentObserver {
    @Override
    public void update(Appointment appointment) {
        System.out.println(
                "Notification for Doctor " + appointment.getDoctor().getName()
                        + " (ID: " + appointment.getDoctor().getId() + "): "
                        + "Your appointment (ID: " + appointment.getId() + ") "
                        + "has been updated to " + appointment.getStatus()
                        + ". Scheduled for: "
                        + LocalDateTime.of(appointment.getDate(), appointment.getTime())
        );
    }
}
