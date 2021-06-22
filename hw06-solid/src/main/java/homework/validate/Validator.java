package homework.validate;

import homework.bill.Bill;
import homework.bill.CountOfBills;
import homework.bill.Tape;

public class Validator implements Valid {

    @Override
    public CountOfBills validate(Tape tapeOfBills, int requestedAmountOfMoney) {
        int minAvailableSum = tapeOfBills.giveMinAvailableSum();
        int currentAmountOfMoney = tapeOfBills.getCurrentAmountOfMoney();
        minAvailableSumCheck(requestedAmountOfMoney, minAvailableSum);
        requestOfMoneyIsInMinSumCheck(requestedAmountOfMoney, minAvailableSum);
        haveEnoughMoneyCheck(currentAmountOfMoney, requestedAmountOfMoney);
        return getCountOfBill(requestedAmountOfMoney, tapeOfBills);
    }

    private CountOfBills getCountOfBill(int requestedAmountOfMoney, Tape tapeOfBills) {
        CountOfBills countOfBills = convertRequestAmountToCountOfBill(requestedAmountOfMoney, tapeOfBills);
        if (countOfBills == null) {
            throw new IllegalArgumentException(String.format("Нет достаточного количества купюр для выдачи суммы в %d", requestedAmountOfMoney));
        }
        return countOfBills;
    }

    private void haveEnoughMoneyCheck(int currentAmountOfMoney, int requestedAmountOfMoney) {
        if (currentAmountOfMoney < requestedAmountOfMoney) {
            throw new IllegalArgumentException(String.format("Не достаточно денежных средств в банкомате для выдачи запрошенной суммы в %d. " +
                    "Имеется только: %d", requestedAmountOfMoney, currentAmountOfMoney));
        }
    }

    private void requestOfMoneyIsInMinSumCheck(int requestedAmountOfMoney, int minAvailableSum) {
        if (requestedAmountOfMoney % minAvailableSum > 0) {
            throw new IllegalArgumentException(String.format("Сумма для снятия должна быть кратна: %d", minAvailableSum));
        }
    }

    private void minAvailableSumCheck(int requestedAmountOfMoney, int minAvailableSum) {
        if (requestedAmountOfMoney < minAvailableSum) {
            throw new IllegalArgumentException(String.format("Минимальная допустимая сумма для снятия: %d. Сумма запрошенная: %d", minAvailableSum, requestedAmountOfMoney));
        }
    }

    private CountOfBills convertRequestAmountToCountOfBill(int requestedAmountOfMoney, Tape tapeOfBills) {
        CountOfBills countOfBills = new CountOfBills();
        Bill[] bills = Bill.values();
        for (Bill bill : bills) {
            int denomination = Bill.getDenomination(bill);
            int requestCountOfBill = requestedAmountOfMoney / denomination;
            int currentCountOfBill = tapeOfBills.giveCountOfBills(bill);
            if (requestCountOfBill > currentCountOfBill) {
                countOfBills.addCountOfBill(bill, currentCountOfBill);
                requestedAmountOfMoney -= denomination * currentCountOfBill;
            } else {
                countOfBills.addCountOfBill(bill, requestCountOfBill);
                requestedAmountOfMoney -= requestCountOfBill * denomination;
            }
            if (requestedAmountOfMoney == 0) {
                break;
            }
        }

        return requestedAmountOfMoney == 0 ? countOfBills : null;
    }
}
