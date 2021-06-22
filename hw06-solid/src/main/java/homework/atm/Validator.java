package homework.atm;

public class Validator {

    public void validate(TapeOfBills tapeOfBills, RequestOfMoney requestOfMoney) {
        int requestedAmountOfMoney = requestOfMoney.getRequestAmount();
        int minAvailableSum = tapeOfBills.giveMinAvailableSum();
        int currentAmountOfMoney = tapeOfBills.getCurrentAmountOfMoney();
        minAvailableSum(requestedAmountOfMoney, minAvailableSum);
        requestOfMoneyIsInMinSum(requestedAmountOfMoney, minAvailableSum);
        iSCurrentAmountMoneyEnough(currentAmountOfMoney, requestedAmountOfMoney);
        isPossibleGiveAmountBills(requestedAmountOfMoney, requestOfMoney, tapeOfBills);
    }

    private void isPossibleGiveAmountBills(int requestedAmountOfMoney, RequestOfMoney requestOfMoney, TapeOfBills tapeOfBills) {
        if (!isPossibleGiveAmount(requestOfMoney, tapeOfBills)) {
            throw new IllegalArgumentException(String.format("Нет достаточного количества купюр для выдачи суммы в %d", requestedAmountOfMoney));
        }
    }

    private void iSCurrentAmountMoneyEnough(int currentAmountOfMoney, int requestedAmountOfMoney) {
        if (currentAmountOfMoney < requestedAmountOfMoney) {
            throw new IllegalArgumentException(String.format("Не достаточно денежных средств в банкомате для выдачи запрошенной суммы в %d. " +
                    "Имеется только: %d", requestedAmountOfMoney, currentAmountOfMoney));
        }
    }

    private void requestOfMoneyIsInMinSum(int requestedAmountOfMoney, int minAvailableSum) {
        if (requestedAmountOfMoney % minAvailableSum > 0) {
            throw new IllegalArgumentException(String.format("Сумма для снятия должна быть кратна: %d", minAvailableSum));
        }
    }

    private void minAvailableSum(int requestedAmountOfMoney, int minAvailableSum) {
        if (requestedAmountOfMoney < minAvailableSum) {
            throw new IllegalArgumentException(String.format("Минимальная допустимая сумма для снятия: %d. Сумма запрошенная: %d", minAvailableSum, requestedAmountOfMoney));
        }
    }

    private boolean isPossibleGiveAmount(RequestOfMoney requestOfMoney, TapeOfBills tapeOfBills) {
        int requestAmount = requestOfMoney.getRequestAmount();
        Bill[] bills = Bill.values();
        for (Bill bill : bills) {
            int denomination = Bill.getDenomination(bill);
            int requestCountOfBill = requestAmount / denomination;
            int currentCountOfBill = tapeOfBills.giveCountOfBills(bill);
            if (requestCountOfBill > currentCountOfBill) {
                requestOfMoney.setCountOfBill(bill, currentCountOfBill);
                requestAmount -= denomination * currentCountOfBill;
            } else {
                requestOfMoney.setCountOfBill(bill, requestCountOfBill);
                requestAmount -= requestCountOfBill * denomination;
            }
            if (requestAmount == 0) {
                break;
            }
        }

        return requestAmount == 0;
    }
}
