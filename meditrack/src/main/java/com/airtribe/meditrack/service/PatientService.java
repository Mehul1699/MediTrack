package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.PatientNotFoundException;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PatientService implements Searchable<Patient> {

    private DataStore<Patient> patients = new DataStore<>();

    public Patient addNewPatient(Patient patient) {
        Validator.validatePatient(patient);
        patient.setId(IdGenerator.getInstance().nextPatientId());
        patients.add(patient);
        return patient;
    }

    public List<Patient> getAll() {
        return patients.getAll();
    }

    @Override
    public Patient search(int id) {
        Patient patient = patients.getById(id);
        if (Objects.nonNull(patient)) {
            return patient;
        } else {
            throw new PatientNotFoundException("No patient with id: " + id);
        }
    }

    @Override
    public List<Patient> search(String name) {

        return patients.getAll().stream()
                .filter(patient -> patient.getName()
                        .toLowerCase()
                        .contains(name.toLowerCase()))
                .toList();

    }

    public void updatePatient(Patient patient) {
        Patient existing = patients.getById(patient.getId());
        existing.setName(patient.getName());
        existing.setAge(patient.getAge());
        existing.setBloodGroup(patient.getBloodGroup());
        existing.setPhoneNumber(patient.getPhoneNumber());
        existing.setMedicalHistory(patient.getMedicalHistory());
    }

    public void deletePatient(Patient patient) {
        patients.remove(patient);
    }
}
