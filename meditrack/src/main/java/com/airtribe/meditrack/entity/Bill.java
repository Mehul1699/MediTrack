package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.BillingType;
import com.airtribe.meditrack.interfaces.Payable;
import com.airtribe.meditrack.service.BillService;

public class Bill extends MedicalEntity {

    private Appointment appointment;
    private double discount;
    private BillingType billingType = BillingType.NORMAL;

    public Bill(Appointment appointment, double discount, BillingType billingType) {
        this.appointment = appointment;
        this.discount = discount;
        this.billingType = billingType;
    }

    @Override
    public String toString() {
        return "Bill{" +
                super.toString() +
                " appointment=" + appointment +
                ", discount=" + discount +
                ", billingType=" + billingType +
                '}';
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public double getDiscount() {
        return discount;
    }

    public BillingType getBillingType() {
        return billingType;
    }
}
