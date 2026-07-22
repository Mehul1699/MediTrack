package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.constants.Gender;
import com.airtribe.meditrack.constants.Specialization;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person implements Cloneable {

    private Specialization specialization;
    private double consultationFees;
    private static final List<LocalTime> availableTimeSlots = Constants.TIME_SLOTS;

    public Doctor(String name, int age, Gender gender, Specialization specialization,
                  double consultationFees) {
        super(name, age, gender);
        this.specialization = specialization;
        this.consultationFees = consultationFees;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                super.toString() +
                " specialization=" + specialization +
                ", consultationFees=" + consultationFees +
                ", availableTimeSlots=" + availableTimeSlots +
                '}';
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public void setConsultationFees(double consultationFees) {
        this.consultationFees = consultationFees;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public double getConsultationFees() {
        return consultationFees;
    }

    public List<LocalTime> getAvailableTimeSlots() {
        return availableTimeSlots;
    }

    @Override
    public Doctor clone() {
        try {
            return (Doctor) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
