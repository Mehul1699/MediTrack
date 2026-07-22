package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.BillingType;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.entity.DiscountedBillingStrategy;
import com.airtribe.meditrack.entity.NormalBillingStrategy;
import com.airtribe.meditrack.interfaces.BillingStrategy;
import com.airtribe.meditrack.interfaces.Payable;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

import java.util.List;

public class BillService implements Payable {

    DataStore<Bill> billDataStore = new DataStore<>();

    public Bill createBill(Bill bill) {
        Validator.validateBill(bill);
        bill.setId(IdGenerator.getInstance().nextBillId());
        billDataStore.add(bill);
        return bill;
    }

    public Bill getBillById(int id) {
        return billDataStore.getById(id);
    }

    public List<Bill> getAllBills() {
        return billDataStore.getAll();
    }

    @Override
    public BillSummary generateBillSummary(Bill bill) {
        BillingStrategy billingStrategy = getStrategy(bill.getBillingType());
        return billingStrategy.generateBill(bill);
    }

    @Override
    public void markAsPaid(int billId) {
        System.out.println("Bill with id: " + billId + " successfully paid");
    }

    private BillingStrategy getStrategy(BillingType type) {
        switch (type) {
            case NORMAL -> {
                return new NormalBillingStrategy();
            }
            case DISCOUNTED -> {
                return new DiscountedBillingStrategy();
            }
        }
        return new NormalBillingStrategy();   // Considering Normal Billing as default billing strategy
    }
}
