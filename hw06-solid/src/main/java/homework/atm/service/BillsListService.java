package homework.atm.service;

import homework.atm.Bill;

import java.util.List;
import java.util.Optional;

public interface BillsListService {

    Optional<CountOfBills> convertRequiredAmountToCountOfBill(int requiredAmount);

    int putBills(List<Bill> billList);

    int getCurrentAmountOfAtm();

    int giveMinAvailableSum();

    List<Bill> takeBills(CountOfBills countOfBills);
}
