package homework.atm;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TapeOfBills {

    private final Map<Bill, List<Bill>> tapeOfBills;

    {
        Bill[] values = Bill.values();
        tapeOfBills = Arrays.stream(values).collect(Collectors.toMap(Function.identity(), bill -> new LinkedList<>()));
    }

    public int getCurrentAmountOfMoney() {
        int currentAmount = 0;
        Set<Map.Entry<Bill, List<Bill>>> entries = tapeOfBills.entrySet();
        for (Map.Entry<Bill, List<Bill>> billListEntry : entries) {
            currentAmount += Bill.getDenomination(billListEntry.getKey()) * billListEntry.getValue().size();
        }

        return currentAmount;
    }

    public void putBills(List<Bill> billList) {
        billList.forEach(bill -> tapeOfBills.get(bill).add(bill));
    }

    public List<Bill> takeBills(RequestOfMoney requestOfMoney) {
        LinkedList<Bill> billsToOutput = new LinkedList<>();
        Map<Bill, Integer> collectionOfMoney = requestOfMoney.getBillCountOfBillMap();

        for (Map.Entry<Bill, Integer> billIntegerEntry : collectionOfMoney.entrySet()) {
            for (int i = 0; i < billIntegerEntry.getValue(); i++) {
                List<Bill> atmBillList = tapeOfBills.get(billIntegerEntry.getKey());
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

    private void transferBillFromAtmToOutput(List<Bill> atmBillList, List<Bill> outputBillList) {
        Bill bill = atmBillList.get(0);
        atmBillList.remove(bill);
        outputBillList.add(bill);
    }
}
