package homework.bill;

import java.util.*;

public interface Tape {

    int getCurrentAmountOfMoney();

    void putBills(Queue<Bill> billList);

    List<Bill> takeBills(CountOfBills countOfBills);

    int giveCountOfBills(Bill bill);

    int giveMinAvailableSum();
}
