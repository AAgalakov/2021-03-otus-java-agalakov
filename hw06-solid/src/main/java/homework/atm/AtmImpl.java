package homework.atm;

import homework.atm.service.BundleOfBanknotes;
import homework.atm.service.BillsListService;
import homework.atm.validation.Validator;

import java.util.List;
import java.util.Optional;

public class AtmImpl implements Atm {

    private final Validator validator;
    private final BillsListService billsListService;

    public AtmImpl(Validator validator, BillsListService billsListService) {
        this.validator = validator;
        this.billsListService = billsListService;
    }

    @Override
    public void pushMoney(List<Bill> billList) {
        System.out.printf("Внесено: %d\n", billsListService.putBills(billList));
    }

    @Override
    public List<Bill> pullMoney(int amountRequestOfMoney) {
        Optional<BundleOfBanknotes> bundleOfBanknotes = billsListService.convertRequiredAmountToCountOfBill(amountRequestOfMoney);
        BundleOfBanknotes count = bundleOfBanknotes.orElse(new BundleOfBanknotes());
        validator.validate(amountRequestOfMoney, billsListService, count);
        return billsListService.takeBills(count);
    }

    @Override
    public void printCurrentAmount() {
        System.out.println("Текущий остаток денежных средств: " + billsListService.getCurrentAmountOfAtm());
    }
}
