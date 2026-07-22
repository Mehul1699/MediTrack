package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.AppointmentObserver;

import java.time.LocalDateTime;

public class PatientObserver implements AppointmentObserver {
    @Override
    public void update(Appointment appointment) {
        System.out.println(
                "Notification for Patient " + appointment.getPatient().getName()
                        + " (ID: " + appointment.getPatient().getId() + "): "
                        + "Your appointment (ID: " + appointment.getId() + ") "
                        + "has been updated to " + appointment.getStatus()
                        + ". Scheduled for: "
                        + LocalDateTime.of(appointment.getDate(), appointment.getTime())
        );
    }
}
