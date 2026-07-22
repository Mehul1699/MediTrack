package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;

import java.util.Objects;

public final class Validator {

    Validator() {
    }

    public static void validateString(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidDataException("This field cannot be empty");
        }
    }

    public static void validatePositiveValue(int value) {
        if (value <= 0) {
            throw new InvalidDataException("Field must be positive. Invalid data");
        }
    }

    public static void validatePositiveValue(double value) {
        if (value <= 0) {
            throw new InvalidDataException("Field must be positive. Invalid data");
        }
    }

    public static void validatePatient(Patient patient) {
        if (Objects.isNull(patient)) {
            throw new InvalidDataException("Patient data can't be null or blank. Invalid patient info");
        }

        validateString(patient.getName());
        validateString(patient.getPhoneNumber());
        validateString(patient.getBloodGroup());
        validatePositiveValue(patient.getAge());
        if (Objects.isNull(patient.getGender())) {
            throw new InvalidDataException("Gender is required");
        }

    }

    public static void validateDoctor(Doctor doctor) {
        if (Objects.isNull(doctor)) {
            throw new InvalidDataException("Doctor data can't be null or blank. Invalid doctor info");
        }

        validateString(doctor.getName());
        validatePositiveValue(doctor.getConsultationFees());
        validatePositiveValue(doctor.getAge());
        if (Objects.isNull(doctor.getGender())) {
            throw new InvalidDataException("Gender is required for doctor");
        }
        if (Objects.isNull(doctor.getSpecialization())) {
            throw new InvalidDataException("Specialization is required for doctor");
        }
        if (doctor.getAvailableTimeSlots() == null || doctor.getAvailableTimeSlots().isEmpty()) {
            throw new InvalidDataException("Please provide available slots for the doctor");
        }

    }

    public static void validateAppointment(Appointment appointment) {

        if (Objects.isNull(appointment)) {
            throw new InvalidDataException("Appointment can't be null");
        }

        if (Objects.isNull(appointment.getPatient())) {
            throw new InvalidDataException("Patient is required with appointment");
        }

        if (Objects.isNull(appointment.getDoctor())) {
            throw new InvalidDataException("Doctor is required with appointment");
        }

        DateValidator.validateAppointmentDate(
                appointment.getDate(), appointment.getTime()
        );
    }

    public static void validateBill(Bill bill){

        if(Objects.isNull(bill)){
            throw new InvalidDataException("Bill can't be null");
        }

        if(Objects.isNull(bill.getAppointment())){
            throw new InvalidDataException("Appointment is required for a Bill to be generated");
        }

    }

}
