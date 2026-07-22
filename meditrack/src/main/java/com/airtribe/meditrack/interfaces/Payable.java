package com.airtribe.meditrack.interfaces;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;

public interface Payable {

    BillSummary generateBillSummary(Bill bill);
    void markAsPaid(int billId);

}
