package homework.atm.validation;

import homework.atm.service.BundleOfBanknotes;
import homework.atm.service.BillsListService;

public interface Validator {

    void validate(int requestedAmountOfMoney, BillsListService billsListService, BundleOfBanknotes bundleOfBanknotes);
}
