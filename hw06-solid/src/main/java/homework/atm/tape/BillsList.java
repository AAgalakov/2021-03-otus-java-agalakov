package homework.atm.tape;

import homework.atm.Bill;
import homework.atm.service.CountOfBills;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface BillsList {

    int putBills(Queue<Bill> billList);

    List<Bill> takeBills(CountOfBills countOfBills);

    int giveCountOfBills(Bill bill);

    int giveMinAvailableSum();

    Map<Bill, Queue<Bill>> getMap();
}
