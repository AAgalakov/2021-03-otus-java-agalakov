package homework.atm.tape;

import homework.atm.Bill;
import homework.atm.service.BundleOfBanknotes;

import java.util.*;
import java.util.stream.Collectors;

public class BillsListImpl implements BillsList {

    private final Map<Bill, Queue<Bill>> billListMap;

    public BillsListImpl(){
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
    public int putBills(List<Bill> billList) {
        int sum = 0;
        for (Bill bill : billList) {
            billListMap.get(bill).add(bill);
            sum += bill.getDenomination();
        }
        return sum;
    }

    @Override
    public List<Bill> takeBills(BundleOfBanknotes bundleOfBanknotes) {
        LinkedList<Bill> billsToOutput = new LinkedList<>();
        Map<Bill, Integer> collectionOfMoney = bundleOfBanknotes.getBillCountOfBillMap();
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
