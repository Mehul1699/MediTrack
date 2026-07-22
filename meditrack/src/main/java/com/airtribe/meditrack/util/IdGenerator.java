package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

    private static final IdGenerator INSTANCE;

    private AtomicInteger patientCounter;
    private AtomicInteger doctorCounter;
    private AtomicInteger appointmentCounter;
    private AtomicInteger billCounter;

    static {
        System.out.println("Initializing Id Generator");
        INSTANCE = new IdGenerator();
    }

    public static IdGenerator getInstance() {
        return INSTANCE;
    }

    private IdGenerator() {
        patientCounter = new AtomicInteger(100);
        doctorCounter = new AtomicInteger(500);
        appointmentCounter = new AtomicInteger(0);
        billCounter = new AtomicInteger(0);
    }

    public int nextPatientId() {
        return patientCounter.incrementAndGet();
    }

    public int nextDoctorId() {
        return doctorCounter.incrementAndGet();
    }

    public int nextAppointmentId() {
        return appointmentCounter.incrementAndGet();
    }

    public int nextBillId() {
        return billCounter.incrementAndGet();
    }

}
