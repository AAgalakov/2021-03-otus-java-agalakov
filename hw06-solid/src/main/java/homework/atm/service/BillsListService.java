package homework.atm.service;

import homework.atm.Bill;

import java.util.List;
import java.util.Queue;

public interface BillsListService {

    CountOfBills convertRequiredAmountToCountOfBill(int requiredAmount);

    int putBills(Queue<Bill> billList);

    int getCurrentAmountOfAtm();

    int giveMinAvailableSum();

    List<Bill> takeBills(CountOfBills countOfBills);
}
