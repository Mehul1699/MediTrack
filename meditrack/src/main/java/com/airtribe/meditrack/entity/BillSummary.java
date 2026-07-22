package com.airtribe.meditrack.entity;

public final class BillSummary {

    private final int billId;
    private final String patientName;
    private final String doctorName;
    private final double tax;
    private final double totalAmount;

    public BillSummary(int billId, String patientName, String doctorName,
                       double tax, double totalAmount) {
        this.billId = billId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.tax = tax;
        this.totalAmount = totalAmount;
    }

    public int getBillId() {
        return billId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public double getTax() {
        return tax;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return """
            ========= BILL SUMMARY =========
            Bill ID          : %d
            Patient Name     : %s
            Doctor Name      : %s
            Tax              : ₹%.2f
            Total Amount     : ₹%.2f
            ================================
            """.formatted(
                billId,
                patientName,
                doctorName,
                tax,
                totalAmount
        );
    }

}
