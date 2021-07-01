package homework.atm.service;

import homework.atm.Bill;
import homework.atm.tape.BillsList;

import java.util.List;
import java.util.Optional;

public class BillsListServiceImpl implements BillsListService {

    private final BillsList billsList;

    public BillsListServiceImpl(BillsList billsList) {
        this.billsList = billsList;
    }

    @Override
    public int putBills(List<Bill> billList) {
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
    public Optional<BundleOfBanknotes> convertRequiredAmountToCountOfBill(int requestedAmountOfMoney) {
        BundleOfBanknotes bundleOfBanknotes = new BundleOfBanknotes();
        Bill[] bills = Bill.values();
        for (Bill bill : bills) {
            int denomination = bill.getDenomination();
            int requestCountOfBill = requestedAmountOfMoney / denomination;
            int currentCountOfBill = billsList.giveCountOfBills(bill);
            if (requestCountOfBill > currentCountOfBill) {
                bundleOfBanknotes.addCountOfBill(bill, currentCountOfBill);
                requestedAmountOfMoney -= denomination * currentCountOfBill;
            } else {
                bundleOfBanknotes.addCountOfBill(bill, requestCountOfBill);
                requestedAmountOfMoney -= requestCountOfBill * denomination;
            }
            if (requestedAmountOfMoney == 0) {
                break;
            }
        }

        return requestedAmountOfMoney == 0 ? Optional.of(bundleOfBanknotes) : Optional.empty();
    }

    @Override
    public List<Bill> takeBills(BundleOfBanknotes bundleOfBanknotes) {
        return billsList.takeBills(bundleOfBanknotes);
    }
}
