package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.interfaces.AppointmentObserver;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AppointmentService {

    private List<AppointmentObserver> observers = new ArrayList<>();
    private DataStore<Appointment> appointmentDataStore = new DataStore<>();

    public void addObserver(AppointmentObserver appointmentObserver) {
        observers.add(appointmentObserver);
    }

    public void addObserver(List<AppointmentObserver> appointmentObservers) {
        observers.addAll(appointmentObservers);
    }

    public void notifyObservers(Appointment appointment) {
        for (AppointmentObserver observer : observers) {
            observer.update(appointment);
        }
    }

    public void removeObserver(AppointmentObserver observer) {
        observers.remove(observer);
    }

    public Appointment scheduleAppointment(Appointment appointment) {
        Validator.validateAppointment(appointment);
        if (!canBookAppointment(appointment.getDoctor(), appointment.getDate(), appointment.getTime())) {
            throw new InvalidDataException("Doctor is already booked for this slot");
        }
        appointment.setStatus(AppointmentStatus.PENDING);
        notifyObservers(appointment);
        appointment.setId(IdGenerator.getInstance().nextAppointmentId());
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        notifyObservers(appointment);
        appointmentDataStore.add(appointment);
        return appointment;
    }

    public void cancelAppointment(int appointmentId) {
        Appointment appointment = appointmentDataStore.getById(appointmentId);
        if (Objects.nonNull(appointment)) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            appointmentDataStore.remove(appointment);
            notifyObservers(appointment);
        } else {
            throw new AppointmentNotFoundException("Appointment not found with id: " + appointmentId + " can't cancel");
        }
    }

    public void rescheduleAppointment(int appointmentId, LocalDate date, LocalTime time) {
        Appointment appointment = appointmentDataStore.getById(appointmentId);
        if (Objects.nonNull(appointment)) {
            if (canBookAppointment(appointment.getDoctor(), date, time)) {
                appointment.setDate(date);
                appointment.setTime(time);
                appointment.setStatus(AppointmentStatus.RESCHEDULED);
                notifyObservers(appointment);
                appointment.setStatus(AppointmentStatus.CONFIRMED);
                notifyObservers(appointment);
            } else {
                throw new InvalidDataException("Doctor is already booked for this slot. Can't reschedule the appointment to this time slot");
            }
        } else {
            throw new AppointmentNotFoundException(
                    "No appointment found with id: " + appointmentId + ". Can't reschedule"
            );
        }
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDataStore.getAll();
    }

    public Appointment getAppointmentById(int id) {
        return appointmentDataStore.getById(id);
    }

    public Map<String, Long> appointmentsPerDoctor() {
        return appointmentDataStore.getAll().stream()
                .collect(Collectors.groupingBy(
                        appointment -> appointment.getDoctor().getName(),
                        Collectors.counting()
                ));
    }

    private boolean canBookAppointment(Doctor doctor, LocalDate date, LocalTime time) {
        return appointmentDataStore.getAll().stream()
                .noneMatch(a ->
                        a.getDoctor().equals(doctor)
                                && a.getDate().equals(date)
                                && a.getTime().equals(time));
    }

}
