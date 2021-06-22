package homework.atm;

import homework.atm.service.CountOfBills;
import homework.atm.service.TapeOfBillsService;
import homework.atm.validation.Validator;

import java.util.List;
import java.util.Queue;

public class Atm {

    private final Validator validator;
    private final TapeOfBillsService tapeOfBillsService;

    public Atm(Validator validator, TapeOfBillsService tapeOfBillsService) {
        this.validator = validator;
        this.tapeOfBillsService = tapeOfBillsService;
    }

    public void pushMoney(Queue<Bill> billList) {
        System.out.printf("Внесено: %d\n", tapeOfBillsService.putBills(billList));
    }

    public List<Bill> pullMoney(int amountRequestOfMoney) {
        CountOfBills countOfBills = tapeOfBillsService.convertRequiredAmountToCountOfBill(amountRequestOfMoney);
        validator.validate(amountRequestOfMoney, tapeOfBillsService, countOfBills);
        return tapeOfBillsService.takeBills(countOfBills);
    }

    public void printCurrentAmount() {
        System.out.println("Текущий остаток денежных средств: " + tapeOfBillsService.getCurrentAmountOfAtm());
    }
}
