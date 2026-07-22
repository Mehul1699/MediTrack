package com.airtribe.meditrack.interfaces;

import com.airtribe.meditrack.entity.Appointment;

public interface AppointmentObserver {
    public void update(Appointment appointment);
}
