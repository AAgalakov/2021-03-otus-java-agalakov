package homework.atm.tape;

import homework.atm.Bill;
import homework.atm.service.CountOfBills;

import java.util.*;
import java.util.stream.Collectors;

public class BillsListImpl implements BillsList {

    private final Map<Bill, Queue<Bill>> billListMap;

    {
        billListMap = new HashMap<>();
        Bill[] values = Bill.values();
        for (Bill bill : values) {
            Queue<Bill> billList = new LinkedList<>();
            for (int i = 0; i < 5; i++) {
                billList.add(bill);
            }
            billListMap.put(bill, billList);
        }
    }

    @Override
    public Map<Bill, Integer> getCountOfBillMap() {
        return billListMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, billQueueEntry -> billQueueEntry.getValue().size()));
    }

    @Override
    public int putBills(Queue<Bill> billList) {
        return billList.stream()
                .map(bill -> {
                    billListMap.get(bill).add(bill);
                    return bill.getDenomination();
                })
                .reduce(0, Integer::sum);
    }

    @Override
    public List<Bill> takeBills(CountOfBills countOfBills) {
        LinkedList<Bill> billsToOutput = new LinkedList<>();
        Map<Bill, Integer> collectionOfMoney = countOfBills.getBillCountOfBillMap();
        for (Map.Entry<Bill, Integer> billIntegerEntry : collectionOfMoney.entrySet()) {
            for (int i = 0; i < billIntegerEntry.getValue(); i++) {
                Queue<Bill> atmBillList = billListMap.get(billIntegerEntry.getKey());
                transferBillFromAtmToOutput(atmBillList, billsToOutput);
            }
        }

        return billsToOutput;
    }

    @Override
    public int giveCountOfBills(Bill bill) {
        return billListMap.get(bill).size();
    }

    @Override
    public int giveMinAvailableSum() {
        return billListMap.entrySet()
                .stream()
                .filter(billListEntry -> billListEntry.getValue().size() > 0)
                .map(billListEntry -> billListEntry.getKey().getDenomination())
                .min(Integer::compareTo)
                .orElseThrow(() -> new IllegalStateException("Кассетница пуста"));
    }

    private void transferBillFromAtmToOutput(Queue<Bill> atmBillList, Queue<Bill> outputBillList) {
        Bill bill = atmBillList.remove();
        outputBillList.add(bill);
    }
}
