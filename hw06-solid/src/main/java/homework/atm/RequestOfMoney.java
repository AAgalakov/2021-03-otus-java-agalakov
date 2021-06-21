package homework.atm;

import java.util.HashMap;
import java.util.Map;

public class RequestOfMoney {

    private final int requestAmount;
    private final Map<Bill, Integer> billCountOfBillMap = new HashMap<>();

    public RequestOfMoney(int requestAmount) {
        this.requestAmount = requestAmount;
    }

    public int getRequestAmount() {
        return requestAmount;
    }

    public void setCountOfBill(Bill bill, Integer countOfBills) {
        billCountOfBillMap.put(bill, countOfBills);
    }

    public Map<Bill, Integer> getBillCountOfBillMap() {
        return billCountOfBillMap;
    }
}
