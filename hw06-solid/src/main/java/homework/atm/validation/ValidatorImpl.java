package homework.atm.validation;

import homework.atm.Bill;
import homework.atm.service.BillsListService;
import homework.atm.service.BundleOfBanknotes;

import java.util.Map;

public class ValidatorImpl implements Validator {

    private static final String NOT_ENOUGH_COUNT_OF_BILL = "Нет достаточного количества купюр для выдачи суммы.";
    private static final String MIN_VALUE_OF_BILL = "Сумма для снятия должна быть кратна: %d";
    private static final String REQUIRED_AMOUNT_OF_MIN_VALUE_OF_BILL = "Минимальная допустимая сумма для снятия: %d. Сумма запрошенная: %d";
    private static final String NOT_ENOUGH_MONEY = "Не достаточно денежных средств в банкомате для выдачи запрошенной" +
            " суммы в %d. Имеется только: %d";

    @Override
    public void validate(int requestedAmountOfMoney, BillsListService billsListService, BundleOfBanknotes bundleOfBanknotes) {
        int minAvailableSum = billsListService.giveMinAvailableSum();
        int currentAmountOfMoney = billsListService.getCurrentAmountOfAtm();
        minAvailableSumCheck(requestedAmountOfMoney, minAvailableSum);
        requestOfMoneyIsInMinSumCheck(requestedAmountOfMoney, minAvailableSum);
        haveEnoughMoneyCheck(requestedAmountOfMoney, currentAmountOfMoney);
        countOfBillIsValidCheck(bundleOfBanknotes.getBillCountOfBillMap());
    }

    private void countOfBillIsValidCheck(Map<Bill, Integer> countOfBillsMap) {
        if (countOfBillsMap.isEmpty()) {
            throw new IllegalArgumentException(NOT_ENOUGH_COUNT_OF_BILL);
        }
    }

    private void haveEnoughMoneyCheck(int requestedAmountOfMoney, int currentAmountOfMoney) {
        if (currentAmountOfMoney < requestedAmountOfMoney) {
            throw new IllegalArgumentException(String.format(NOT_ENOUGH_MONEY, requestedAmountOfMoney, currentAmountOfMoney));
        }
    }

    private void requestOfMoneyIsInMinSumCheck(int requestedAmountOfMoney, int minAvailableSum) {
        if (requestedAmountOfMoney % minAvailableSum > 0) {
            throw new IllegalArgumentException(String.format(MIN_VALUE_OF_BILL, minAvailableSum));
        }
    }

    private void minAvailableSumCheck(int requestedAmountOfMoney, int minAvailableSum) {
        if (requestedAmountOfMoney < minAvailableSum) {
            throw new IllegalArgumentException(String.format(REQUIRED_AMOUNT_OF_MIN_VALUE_OF_BILL, minAvailableSum, requestedAmountOfMoney));
        }
    }
}
