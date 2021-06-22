package homework.atm.service;

import homework.atm.Bill;
import homework.atm.tape.TapeOfBills;

import java.util.List;
import java.util.Queue;

public class TapeOfBillsServiceImpl implements TapeOfBillsService {

    private final TapeOfBills tapeOfBills;

    public TapeOfBillsServiceImpl(TapeOfBills tapeOfBills) {
        this.tapeOfBills = tapeOfBills;
    }

    @Override
    public int putBills(Queue<Bill> billList) {
        return tapeOfBills.putBills(billList);
    }

    @Override
    public int giveMinAvailableSum() {
        return tapeOfBills.giveMinAvailableSum();
    }

    @Override
    public int getCurrentAmountOfAtm() {
        return tapeOfBills.getMap().entrySet().stream()
                .map(billQueueEntry -> billQueueEntry.getKey().getDenomination() * billQueueEntry.getValue().size())
                .reduce(0, Integer::sum);
    }

    @Override
    public CountOfBills convertRequiredAmountToCountOfBill(int requestedAmountOfMoney) {
        CountOfBills countOfBills = new CountOfBills();
        Bill[] bills = Bill.values();
        for (Bill bill : bills) {
            int denomination = bill.getDenomination();
            int requestCountOfBill = requestedAmountOfMoney / denomination;
            int currentCountOfBill = tapeOfBills.giveCountOfBills(bill);
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
        return tapeOfBills.takeBills(countOfBills);
    }
}
