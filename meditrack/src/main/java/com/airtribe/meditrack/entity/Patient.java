package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Gender;

public class Patient extends Person implements Cloneable {

    private String phoneNumber;
    private String medicalHistory;
    private String bloodGroup;

    public Patient(String name, int age, Gender gender, String phone, String medicalHistory, String bloodGroup) {
        super(name, age, gender);
        this.phoneNumber = phone;
        this.medicalHistory = medicalHistory;
        this.bloodGroup = bloodGroup;
    }

    @Override
    public String toString() {
        return "Patient{" +
                super.toString() +
                " phoneNumber='" + phoneNumber + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                '}';
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Override
    public Patient clone() {
        try {
            return (Patient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
