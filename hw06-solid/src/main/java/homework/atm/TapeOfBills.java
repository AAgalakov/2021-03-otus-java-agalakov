package homework.atm;

import java.util.*;

public class TapeOfBills {

    private final Map<Bill, Queue<Bill>> tapeOfBills;

    {
        tapeOfBills = new HashMap<>();
        Bill[] values = Bill.values();
        for (Bill bill : values) {
            Queue<Bill> billList = new LinkedList<>();
            for (int i = 0; i < 10; i++) {
                billList.add(bill);
            }
            tapeOfBills.put(bill, billList);
        }
    }

    public int getCurrentAmountOfMoney() {
        int currentAmount = 0;
        Set<Map.Entry<Bill, Queue<Bill>>> entries = tapeOfBills.entrySet();
        for (Map.Entry<Bill, Queue<Bill>> billListEntry : entries) {
            currentAmount += Bill.getDenomination(billListEntry.getKey()) * billListEntry.getValue().size();
        }

        return currentAmount;
    }

    public void putBills(Queue<Bill> billList) {
        billList.forEach(bill -> tapeOfBills.get(bill).add(bill));
    }

    public List<Bill> takeBills(RequestOfMoney requestOfMoney) {
        LinkedList<Bill> billsToOutput = new LinkedList<>();
        Map<Bill, Integer> collectionOfMoney = requestOfMoney.getBillCountOfBillMap();
        for (Map.Entry<Bill, Integer> billIntegerEntry : collectionOfMoney.entrySet()) {
            for (int i = 0; i < billIntegerEntry.getValue(); i++) {
                Queue<Bill> atmBillList = tapeOfBills.get(billIntegerEntry.getKey());
                transferBillFromAtmToOutput(atmBillList, billsToOutput);
            }
        }

        return billsToOutput;
    }

    public int giveCountOfBills(Bill bill) {
        return tapeOfBills.get(bill).size();
    }

    public int giveMinAvailableSum() {
        return tapeOfBills.entrySet()
                .stream()
                .filter(billListEntry -> billListEntry.getValue().size() > 0)
                .map(billListEntry -> Bill.getDenomination(billListEntry.getKey()))
                .min(Integer::compareTo)
                .orElseThrow(() -> new IllegalStateException("Кассетница пуста"));
    }

    private void transferBillFromAtmToOutput(Queue<Bill> atmBillList, Queue<Bill> outputBillList) {
        Bill bill = atmBillList.remove();
        outputBillList.add(bill);
    }
}
