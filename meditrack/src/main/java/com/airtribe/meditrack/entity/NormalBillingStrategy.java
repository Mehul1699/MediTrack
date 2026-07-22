package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.BillingStrategy;

public class NormalBillingStrategy implements BillingStrategy {
    @Override
    public BillSummary generateBill(Bill bill) {
        Appointment appointment = bill.getAppointment();
        double consultationFee = appointment
                .getDoctor().getConsultationFees();

        double tax = consultationFee * Constants.TAX_RATE;
        double total = consultationFee + tax;

        return new BillSummary(
                bill.getId(), appointment.getPatient().getName(),
                appointment.getDoctor().getName(), tax, total
        );
    }
}
