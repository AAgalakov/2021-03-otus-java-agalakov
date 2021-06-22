package homework.atm;

import homework.atm.service.CountOfBills;
import homework.atm.service.BillsListService;
import homework.atm.validation.Validator;

import java.util.List;
import java.util.Queue;

public class Atm {

    private final Validator validator;
    private final BillsListService billsListService;

    public Atm(Validator validator, BillsListService billsListService) {
        this.validator = validator;
        this.billsListService = billsListService;
    }

    public void pushMoney(Queue<Bill> billList) {
        System.out.printf("Внесено: %d\n", billsListService.putBills(billList));
    }

    public List<Bill> pullMoney(int amountRequestOfMoney) {
        CountOfBills countOfBills = billsListService.convertRequiredAmountToCountOfBill(amountRequestOfMoney);
        validator.validate(amountRequestOfMoney, billsListService, countOfBills);
        return billsListService.takeBills(countOfBills);
    }

    public void printCurrentAmount() {
        System.out.println("Текущий остаток денежных средств: " + billsListService.getCurrentAmountOfAtm());
    }
}
