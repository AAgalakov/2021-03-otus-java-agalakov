package homework.atm.validation;

import homework.atm.service.CountOfBills;
import homework.atm.service.BillsListService;

public interface Validator {

    void validate(int requestedAmountOfMoney, BillsListService billsListService, CountOfBills countOfBills);
}
