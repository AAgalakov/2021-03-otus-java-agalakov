package homework.atm;

import java.util.List;

public class Atm {

    private final TapeOfBills tapeOfBills = new TapeOfBills();

    public void takeMoney(List<Bill> billList) {
        tapeOfBills.putBills(billList);
        printCurrentAmount();
    }

    public List<Bill> takeRequest(int amountRequestOfMoney) {
        RequestOfMoney requestOfMoney = new RequestOfMoney(amountRequestOfMoney);
        new Validation(tapeOfBills, requestOfMoney).validate();
        List<Bill> billList = tapeOfBills.takeBills(requestOfMoney);
        printCurrentAmount();
        return billList;
    }

    public void printCurrentAmount() {
        System.out.println("Текущий остаток денежных средств: " + tapeOfBills.getCurrentAmountOfMoney());
    }
}
