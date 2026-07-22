package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.DoctorNotFoundException;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

import java.util.List;
import java.util.Objects;

public class DoctorService implements Searchable<Doctor> {

    private DataStore<Doctor> doctors = new DataStore<>();

    public Doctor addNewDoctor(Doctor doctor) {
        Validator.validateDoctor(doctor);
        doctor.setId(IdGenerator.getInstance().nextDoctorId());
        doctors.add(doctor);
        return doctor;
    }

    public List<Doctor> getAllDoctors() {
        return doctors.getAll();
    }

    @Override
    public Doctor search(int id) {
        Doctor doctor = doctors.getById(id);
        if (Objects.nonNull(doctor)) {
            return doctor;
        } else {
            throw new DoctorNotFoundException("Doctor not found with id: " + id);
        }
    }

    @Override
    public List<Doctor> search(String name) {
        return doctors.getAll().stream()
                .filter(doctor -> doctor.getName()
                        .toLowerCase()
                        .contains(name.toLowerCase()))
                .toList();
    }

    public List<Doctor> searchDoctorsBySpecialization(Specialization specialization) {
        return doctors.getAll().stream()
                .filter(doctor -> doctor.getSpecialization()
                        .equals(specialization))
                .toList();
    }

    public void updateDoctor(Doctor doctor) {
        Doctor existingDoctor = doctors.getById(doctor.getId());
        existingDoctor.setName(doctor.getName());
        existingDoctor.setGender(doctor.getGender());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setConsultationFees(doctor.getConsultationFees());
        existingDoctor.setAge(doctor.getAge());
    }

    public void deleteDoctor(Doctor doctor) {
        doctors.remove(doctor);
    }

    public double averageConsultationFee() {
        return doctors.getAll().stream()
                .mapToDouble(Doctor::getConsultationFees)
                .average()
                .orElse(0.0);
    }

}
