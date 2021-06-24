package homework.atm;

import java.util.List;

public interface Atm {
    void pushMoney(List<Bill> billList);
    List<Bill> pullMoney(int amountRequestOfMoney);
    void printCurrentAmount();
}
