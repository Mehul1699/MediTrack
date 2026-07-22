package com.airtribe.meditrack.interfaces;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;

public interface BillingStrategy {
    BillSummary generateBill(Bill bill);
}
