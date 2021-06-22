package homework.atm;

import java.util.List;
import java.util.Queue;

public class Atm {

    private final TapeOfBills tapeOfBills;
    private final Validator validator;

    public Atm(TapeOfBills tapeOfBills, Validator validator) {
        this.tapeOfBills = tapeOfBills;
        this.validator = validator;
    }

    public void pushMoney(Queue<Bill> billList) {
        tapeOfBills.putBills(billList);
        printCurrentAmount();
    }

    public List<Bill> pullMoney(int amountRequestOfMoney) {
        RequestOfMoney requestOfMoney = new RequestOfMoney(amountRequestOfMoney);
        validator.validate(tapeOfBills, requestOfMoney);
        List<Bill> billList = tapeOfBills.takeBills(requestOfMoney);
        printCurrentAmount();
        return billList;
    }

    public void printCurrentAmount() {
        System.out.println("Текущий остаток денежных средств: " + tapeOfBills.getCurrentAmountOfMoney());
    }
}
