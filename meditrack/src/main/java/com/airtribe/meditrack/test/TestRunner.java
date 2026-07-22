package com.airtribe.meditrack.test;

import com.airtribe.meditrack.constants.BillingType;
import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.constants.Gender;
import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.interfaces.AppointmentObserver;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TestRunner {

    private static final DoctorService doctorService = new DoctorService();
    private static final PatientService patientService = new PatientService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final BillService billingService = new BillService();

    static void main() {
        try {
            // Creating Patient
            Patient patient1 = new Patient(
                    "Raju Yadav", 24, Gender.MALE,
                    "98647728763", "Having neurologic issues",
                    "O+"
            );
            Patient patient2 = new Patient(
                    "Pushpa Rani", 40, Gender.FEMALE,
                    "27947777289", "Having Heart issues",
                    "A-"
            );
            Patient patient3 = new Patient(
                    "Hari Kumar", 30, Gender.MALE,
                    "99994392992", "Liver cancer",
                    "AB+"
            );
            Patient patient4 = new Patient(
                    "Kushal Yadav", 42, Gender.MALE,
                    "88888672234", "Heart disease",
                    "B+"
            );
            Patient patient5 = new Patient(
                    "Gaurav Kumar", 42, Gender.MALE,
                    "88889832234", "Head ache",
                    "B+"
            );

            // Adding Patient
            patientService.addNewPatient(patient1);
            int id1 = patient1.getId();
            System.out.println("Patient created with id: " + patient1.getId());
            patientService.addNewPatient(patient2);
            System.out.println("Patient created with id: " + patient2.getId());
            patientService.addNewPatient(patient3);
            System.out.println("Patient created with id: " + patient3.getId());
            patientService.addNewPatient(patient4);
            System.out.println("Patient created with id: " + patient4.getId());
            System.out.println("====================================================");

            // Creating doctors
            Doctor doctor = new Doctor(
                    "Avinash Chadda", 34, Gender.MALE,
                    Specialization.NEUROLOGIST, 1000
            );
            Doctor doctor2 = new Doctor(
                    "Drishti Kumar", 22, Gender.FEMALE,
                    Specialization.HEART, 2000
            );
            Doctor doctor3 = new Doctor(
                    "Jai Kumar", 28, Gender.MALE,
                    Specialization.CANCER, 3000
            );
            // Adding doctors
            doctorService.addNewDoctor(doctor);
            System.out.println("Doctor created with id: " + doctor.getId());
            doctorService.addNewDoctor(doctor2);
            System.out.println("Doctor created with id: " + doctor2.getId());
            doctorService.addNewDoctor(doctor3);
            System.out.println("Doctor created with id: " + doctor3.getId());
            System.out.println("====================================================");

            // Demonstrating Average consultation fee
            System.out.println("Average consultation fee");
            System.out.println(doctorService.averageConsultationFee());
            System.out.println("====================================================");

            // Adding Observers
            AppointmentObserver appointmentObserver1 = new PatientObserver();
            AppointmentObserver appointmentObserver2 = new DoctorObserver();
            AppointmentObserver appointmentObserver3 = new BillingObserver();
            appointmentService.addObserver(
                    List.of(appointmentObserver1, appointmentObserver2, appointmentObserver3)
            );

            // Searching for Doctor by specialization
            System.out.println("Searching doctor by specialization Cancer");
            List<Doctor> cancerDoctors = doctorService.searchDoctorsBySpecialization(Specialization.CANCER);
            System.out.println("Cancer doctors: " + cancerDoctors);
            System.out.println("Searching doctor by specialization heart");
            List<Doctor> heartDoctors = doctorService.searchDoctorsBySpecialization(Specialization.HEART);
            System.out.println("Heart specialists: " + heartDoctors);
            System.out.println("====================================================");

            // Searching Patient by Id
            Patient patientById = searchPatientById(id1);
            System.out.println("Output of patient search by id: " + id1 + " is: " + patientById);
            System.out.println("====================================================");

            // Searching Patient by Name
            List<Patient> patientList = patientService.search("Yadav");
            System.out.println("Patient by name: " + patientList);
            System.out.println("====================================================");

            // Scheduling Appointment 1
            Appointment appointment1 = new Appointment(
                    cancerDoctors.getFirst(), patient3, LocalDate.of(2026, 9, 25), LocalTime.of(14, 0, 0)
            );
            appointmentService.scheduleAppointment(appointment1);
            System.out.println("Appointment scheduled: " + appointment1);
            System.out.println("====================================================");

            // Creating bill
            Bill bill = new Bill(
                    appointment1, appointment1.getDoctor().getConsultationFees() * Constants.DISCOUNT, BillingType.DISCOUNTED
            );
            billingService.createBill(bill);
            System.out.println("Created bill with id: " + bill.getId());
            System.out.println("====================================================");

            // Generating Bill Summary
            BillSummary summary = billingService.generateBillSummary(bill);
            System.out.println(summary);
            billingService.markAsPaid(bill.getId());
            System.out.println("====================================================");

            // Scheduling Appointment 2
            Appointment appointment2 = new Appointment(
                    heartDoctors.getFirst(), patient2, LocalDate.of(2026, 9, 22), LocalTime.of(9, 0, 0)
            );
            appointmentService.scheduleAppointment(appointment2);
            System.out.println("Appointment scheduled: " + appointment2);
            System.out.println("====================================================");

            // Creating Bill for Appointment 2
            Bill bill1 = new Bill(
                    appointment2, appointment2.getDoctor().getConsultationFees(), BillingType.NORMAL
            );
            billingService.createBill(bill1);
            System.out.println("Created bill with id: " + bill1.getId());
            System.out.println("====================================================");

            // Generating Bill Summary for Appointment 2
            BillSummary summary1 = billingService.generateBillSummary(bill1);
            System.out.println(summary1);
            billingService.markAsPaid(bill1.getId());
            System.out.println("====================================================");

            System.out.println("Appointments per doctor: ");
            System.out.println(appointmentService.appointmentsPerDoctor());
            System.out.println("====================================================");

            // User trying to book already booked slot - Will throw exception
            scheduleAlreadyBookedAppointment(heartDoctors.getFirst(), patient4);
            System.out.println("====================================================");

            // Patient search by id which doesn't exist
            Patient patientByIdErrorCase = searchPatientById(44);
            System.out.println("====================================================");

            // Demonstrating shallow copy
            Patient shallow = patient5;
            shallow.setBloodGroup("O+");
            System.out.println("Blood group of patient shallow after update: " + shallow.getBloodGroup());
            System.out.println("Blood group of patient5 after update: " + patient5.getBloodGroup());

            // Demonstrating deep copy
            Appointment clone = appointment1.clone();

            clone.getPatient().setName("Rahul");

            System.out.println(appointment1.getPatient().getName());
            System.out.println(clone.getPatient().getName());

            // Printing all data
            System.out.println("All data: ======================================>");
            System.out.println("All Patients: " + patientService.getAll());
            System.out.println("All Doctors: " + doctorService.getAllDoctors());
            System.out.println("All Appointments: " + appointmentService.getAllAppointments());
            System.out.println("All Bills: " + billingService.getAllBills());

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }

    private static void scheduleAlreadyBookedAppointment(Doctor doctor, Patient patient) {
        try {
            Appointment appointment3 = new Appointment(
                    doctor, patient, LocalDate.of(2026, 9, 22), LocalTime.of(9, 0, 0)
            );
            appointmentService.scheduleAppointment(appointment3);
        } catch (Exception e) {
            System.out.println("ERROR in scheduleAlreadyBookedAppointment: " + e.getLocalizedMessage());
        }
    }

    private static Patient searchPatientById(int id) {
        try {
            return patientService.search(id);
        } catch (Exception e) {
            System.out.println("ERROR in searchPatientById: " + e.getLocalizedMessage());
        }
        return null;
    }

}
