package homework.atm;

import homework.bill.Bill;
import homework.bill.Tape;
import homework.bill.CountOfBills;
import homework.validate.Valid;

import java.util.List;
import java.util.Queue;

public class Atm {

    private final Tape tapeOfBills;
    private final Valid validator;

    public Atm(Tape tapeOfBills, Valid validator) {
        this.tapeOfBills = tapeOfBills;
        this.validator = validator;
    }

    public void pushMoney(Queue<Bill> billList) {
        tapeOfBills.putBills(billList);
        printCurrentAmount();
    }

    public List<Bill> pullMoney(int amountRequestOfMoney) {
        CountOfBills countOfBills = validator.validate(tapeOfBills, amountRequestOfMoney);
        List<Bill> billList = tapeOfBills.takeBills(countOfBills);
        printCurrentAmount();
        return billList;
    }

    public void printCurrentAmount() {
        System.out.println("Текущий остаток денежных средств: " + tapeOfBills.getCurrentAmountOfMoney());
    }
}
