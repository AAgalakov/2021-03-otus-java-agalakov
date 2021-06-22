package homework.atm.validation;

import homework.atm.service.CountOfBills;
import homework.atm.service.TapeOfBillsService;

public interface Validator {

    void validate(int requestedAmountOfMoney, TapeOfBillsService tapeOfBillsService, CountOfBills countOfBills);
}
