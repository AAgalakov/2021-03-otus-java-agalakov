package homework.atm.service;

import homework.atm.Bill;
import homework.atm.tape.BillsList;

import java.util.List;
import java.util.Queue;

public class BillsListServiceImpl implements BillsListService {

    private final BillsList billsList;

    public BillsListServiceImpl(BillsList billsList) {
        this.billsList = billsList;
    }

    @Override
    public int putBills(Queue<Bill> billList) {
        return billsList.putBills(billList);
    }

    @Override
    public int giveMinAvailableSum() {
        return billsList.giveMinAvailableSum();
    }

    @Override
    public int getCurrentAmountOfAtm() {
        return billsList.getCountOfBillMap().entrySet()
                .stream()
                .map(billQueueEntry -> billQueueEntry.getKey().getDenomination() * billQueueEntry.getValue())
                .reduce(0, Integer::sum);
    }

    @Override
    public CountOfBills convertRequiredAmountToCountOfBill(int requestedAmountOfMoney) {
        CountOfBills countOfBills = new CountOfBills();
        Bill[] bills = Bill.values();
        for (Bill bill : bills) {
            int denomination = bill.getDenomination();
            int requestCountOfBill = requestedAmountOfMoney / denomination;
            int currentCountOfBill = billsList.giveCountOfBills(bill);
            if (requestCountOfBill > currentCountOfBill) {
                countOfBills.addCountOfBill(bill, currentCountOfBill);
                requestedAmountOfMoney -= denomination * currentCountOfBill;
            } else {
                countOfBills.addCountOfBill(bill, requestCountOfBill);
                requestedAmountOfMoney -= requestCountOfBill * denomination;
            }
            if (requestedAmountOfMoney == 0) {
                break;
            }
        }

        return requestedAmountOfMoney == 0 ? countOfBills : null;
    }

    @Override
    public List<Bill> takeBills(CountOfBills countOfBills) {
        return billsList.takeBills(countOfBills);
    }
}
