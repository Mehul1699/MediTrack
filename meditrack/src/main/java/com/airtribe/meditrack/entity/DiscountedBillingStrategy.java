package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.BillingStrategy;

public class DiscountedBillingStrategy implements BillingStrategy {
    @Override
    public BillSummary generateBill(Bill bill) {
        Appointment appointment = bill.getAppointment();
        double consultationFees = appointment.getDoctor().getConsultationFees();
        double tax = (consultationFees - bill.getDiscount()) * Constants.TAX_RATE;
        double total = (consultationFees - bill.getDiscount()) + tax;

        return new BillSummary(
                bill.getId(), appointment.getPatient().getName(),
                appointment.getDoctor().getName(),
                tax, total
        );
    }
}
