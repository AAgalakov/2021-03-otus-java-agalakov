package homework.atm.service;

import homework.atm.Bill;

import java.util.HashMap;
import java.util.Map;

public class CountOfBills {

    private final Map<Bill, Integer> billCountOfBillMap = new HashMap<>();

    public void addCountOfBill(Bill bill, Integer countOfBills) {
        billCountOfBillMap.put(bill, countOfBills);
    }

    public Map<Bill, Integer> getBillCountOfBillMap() {
        return billCountOfBillMap;
    }
}
