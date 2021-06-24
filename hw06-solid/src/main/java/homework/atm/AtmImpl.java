package homework.atm;

import homework.atm.service.CountOfBills;
import homework.atm.service.BillsListService;
import homework.atm.validation.Validator;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

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
        Optional<CountOfBills> countOfBills = billsListService.convertRequiredAmountToCountOfBill(amountRequestOfMoney);
        CountOfBills count = countOfBills.orElse(new CountOfBills());
        validator.validate(amountRequestOfMoney, billsListService, count);
        return billsListService.takeBills(count);
    }

    @Override
    public void printCurrentAmount() {
        System.out.println("Текущий остаток денежных средств: " + billsListService.getCurrentAmountOfAtm());
    }
}
