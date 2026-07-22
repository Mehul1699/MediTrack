package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.BillingType;
import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.constants.Gender;
import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.interfaces.AppointmentObserver;
import com.airtribe.meditrack.interfaces.BillingStrategy;
import com.airtribe.meditrack.interfaces.Payable;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final DoctorService doctorService = new DoctorService();
    private static final PatientService patientService = new PatientService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final BillService billingService = new BillService();

    static void main() {
        AppointmentObserver appointmentObserver1 = new PatientObserver();
        AppointmentObserver appointmentObserver2 = new DoctorObserver();
        AppointmentObserver appointmentObserver3 = new BillingObserver();
        appointmentService.addObserver(List.of(appointmentObserver1, appointmentObserver2, appointmentObserver3));
        int choice = 0;
        while (choice != 10) {
            System.out.println("===== Meditrack =====");
            System.out.println("Please select an option");
            System.out.println("1. Add doctor");
            System.out.println("2. Add patient");
            System.out.println("3. Search Patient by Id");
            System.out.println("4. Search Patient by Name");
            System.out.println("5. Display all doctors");
            System.out.println("6. Schedule Appointment");
            System.out.println("7. Cancel appointment");
            System.out.println("8. Reschedule appointment");
            System.out.println("9. Generate Bill");
            System.out.println("10. Exit");

            System.out.println("Please enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    createDoctor(scanner);
                    break;
                case 2:
                    createPatient(scanner);
                    break;
                case 3:
                    System.out.println("Please enter id of patient to be searched: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    searchPatientById(id);
                    break;
                case 4:
                    System.out.println("Please enter name to be searched: ");
                    String name = scanner.nextLine();
                    searchPatientByName(name);
                    break;
                case 5:
                    displayAllDoctors();
                    break;
                case 6:
                    scheduleAppointment(scanner);
                    break;
                case 7:
                    System.out.println("Please enter appointment id to be cancelled: ");
                    int appointmentId = scanner.nextInt();
                    scanner.nextLine();
                    cancelAppointment(appointmentId);
                    break;
                case 8:
                    rescheduleAppointment(scanner);
                    break;
                case 9:
                    generateBill(scanner);
                    break;
                default:
                    System.out.println("======= THANK YOU ======");
            }
        }

    }

    public static void createDoctor(Scanner scanner) {

        System.out.println("Please enter doctor name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please select gender: ");
        for (Gender gender : Gender.values()) {
            System.out.println(gender);
        }
        Gender gender = null;

        while (gender == null) {
            try {
                System.out.print("Enter Gender (MALE/FEMALE/OTHER): ");
                gender = Gender.valueOf(scanner.next().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid gender. Try again.");
            }
        }
        System.out.println("Please select gender: ");
        for (Specialization spec : Specialization.values()) {
            System.out.println(spec);
        }
        Specialization specialization = null;
        while (specialization == null) {
            try {
                System.out.print("Enter Gender (CANCER/HEART/LIVER/NEUROLOGIST): ");
                specialization = Specialization.valueOf(scanner.next().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid specialization. Try again.");
            }
        }

        System.out.println("Enter consultation fees: ");
        double consultationFees = scanner.nextDouble();
        scanner.nextLine();

        Doctor doctor = new Doctor(
                name, age, gender, specialization, consultationFees
        );
        doctorService.addNewDoctor(doctor);
        System.out.println("Doctor added: " + doctor);

    }

    public static void createPatient(Scanner scanner) {

        System.out.println("Please enter patient name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please select gender: ");
        for (Gender gender : Gender.values()) {
            System.out.println(gender);
        }
        Gender gender = null;

        while (gender == null) {
            try {
                System.out.print("Enter Gender (MALE/FEMALE/OTHER): ");
                gender = Gender.valueOf(scanner.next().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid gender. Try again.");
            }
        }
        scanner.nextLine();
        System.out.println("Please enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.println("Please enter medical history: ");
        String medicalHistory = scanner.nextLine();

        System.out.println("Please enter blood group: ");
        String bloodGroup = scanner.nextLine();

        Patient patient = new Patient(
                name, age, gender, phoneNumber, medicalHistory, bloodGroup
        );
        patientService.addNewPatient(patient);
        System.out.println("Patient created: " + patient);

    }

    public static void searchPatientById(int id) {
        try {
            Patient patient = patientService.search(id);
            if (Objects.nonNull(patient)) {
                System.out.println(patient);
            }
        } catch (Exception e) {
            System.out.println("Error in search patient by id: " + e.getLocalizedMessage());
        }
    }

    public static void searchPatientByName(String name) {
        List<Patient> patients = patientService.search(name);
        if (patients.isEmpty()) {
            System.out.println("Sorry! No patient exist with the name: " + name);
        } else {
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }

    public static void displayAllDoctors() {
        List<Doctor> allDoctors = doctorService.getAllDoctors();
        for (Doctor doctor : allDoctors) {
            System.out.println(doctor);
        }
    }

    public static void scheduleAppointment(Scanner scanner) {
        System.out.println("Please enter patient id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Patient patient = null;
        try {
            patient = patientService.search(id);
        } catch (Exception e) {
            System.out.println("Error in schedule appointment: " + e.getLocalizedMessage());
        }
        if (Objects.isNull(patient)) {
            return;
        }

        System.out.println("Please enter doctor id: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine();
        Doctor doctor = null;
        try {
            doctor = doctorService.search(doctorId);
        } catch (Exception e) {
            System.out.println("Error in schedule appointment: " + e.getLocalizedMessage());
        }
        if (Objects.isNull(doctor)) {
            return;
        }

        System.out.print("Enter appointment date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.next());

        System.out.print("Enter appointment time (HH:mm): ");
        System.out.println("Available slots: " + Constants.TIME_SLOTS);
        LocalTime time = LocalTime.parse(scanner.next());

        Appointment appointment = new Appointment(
                doctor, patient, date, time
        );
        appointmentService.scheduleAppointment(appointment);
        System.out.println("Appointment scheduled: " + appointment);
    }

    public static void cancelAppointment(int id) {
        try {
            appointmentService.cancelAppointment(id);
            System.out.println("Appointment cancelled");
        } catch (Exception e) {
            System.out.println("Exception in cancelling appointment: " + e.getLocalizedMessage());
        }
    }

    public static void rescheduleAppointment(Scanner scanner) {
        try {
            System.out.println("Please enter appointment id to be rescheduled");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter appointment reschedule date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(scanner.next());

            System.out.print("Enter appointment reschedule time (HH:mm): ");
            System.out.println("Available slots: " + Constants.TIME_SLOTS);
            LocalTime time = LocalTime.parse(scanner.next());
            appointmentService.rescheduleAppointment(id, date, time);
            System.out.println("Appointment rescheduled successfully");
        } catch (Exception e) {
            System.out.println("Exception in rescheduling appointment: " + e.getLocalizedMessage());
        }
    }

    public static void generateBill(Scanner scanner) {
        try {
            System.out.println("Please enter appointment id: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Appointment appointment = appointmentService.getAppointmentById(id);
            if (Objects.nonNull(appointment)) {
                Patient patient = appointment.getPatient();
                double discount = 0.0;
                BillingType billingType = BillingType.NORMAL;
                if (patient.getAge() >= 40) {
                    discount = Constants.DISCOUNT;
                    billingType = BillingType.DISCOUNTED;
                }
                Bill bill = new Bill(
                        appointment, discount, billingType
                );
                billingService.createBill(bill);
                System.out.println("Generating bill summary");
                BillSummary billSummary = billingService.generateBillSummary(bill);
                System.out.println(billSummary);
            } else {
                System.out.println("Error in generate Bill: appointment not found with id: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error in generate bill: " + e.getLocalizedMessage());
        }
    }


}
