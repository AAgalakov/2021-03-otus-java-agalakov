package homework.atm;

import homework.atm.service.BillsListService;
import homework.atm.service.CountOfBills;
import homework.atm.validation.Validator;

import java.util.List;
import java.util.Queue;

public interface Atm {
    void pushMoney(List<Bill> billList);
    List<Bill> pullMoney(int amountRequestOfMoney);
    void printCurrentAmount();
}
