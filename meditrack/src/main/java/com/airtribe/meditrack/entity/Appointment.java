package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment extends MedicalEntity implements Cloneable {

    private Doctor doctor;
    private Patient patient;
    private AppointmentStatus status;
    private LocalDate date;
    private LocalTime time;

    public Appointment(Doctor doctor, Patient patient,
                       LocalDate date, LocalTime time) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                super.toString() +
                " doctor=" + doctor +
                ", patient=" + patient +
                ", status=" + status +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

    @Override
    public Appointment clone() {
        try {
            Appointment clone = (Appointment) super.clone();
            clone.setPatient(patient.clone());
            clone.setDoctor(doctor.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
