package homework.atm;

public class Validation {

    private final TapeOfBills tapeOfBills;
    private final RequestOfMoney requestOfMoney;
    private final int requestedAmountOfMoney;
    private final int minAvailableSum;
    private final int currentAmountOfMoney;

    public Validation(TapeOfBills tapeOfBills, RequestOfMoney requestOfMoney) {
        this.tapeOfBills = tapeOfBills;
        this.requestOfMoney = requestOfMoney;
        this.requestedAmountOfMoney = requestOfMoney.getRequestAmount();
        this.minAvailableSum = tapeOfBills.giveMinAvailableSum();
        this.currentAmountOfMoney = tapeOfBills.getCurrentAmountOfMoney();
    }

    public void validate() {
        minAvailableSum();
        requestOfMoneyIsInMinSum();
        iSCurrentAmountMoneyEnough();
        isPossibleGiveAmountBills();
    }

    private void isPossibleGiveAmountBills() {
        if (!isPossibleGiveAmount(requestOfMoney)) {
            throw new IllegalArgumentException(String.format("Нет достаточного количества купюр для выдачи суммы в %d", requestedAmountOfMoney));
        }
    }

    private void iSCurrentAmountMoneyEnough() {
        if (currentAmountOfMoney < requestedAmountOfMoney) {
            throw new IllegalArgumentException(String.format("Не достаточно денежных средств в банкомате для выдачи запрошенной суммы в %d. " +
                    "Имеется только: %d", requestedAmountOfMoney, currentAmountOfMoney));
        }
    }

    private void requestOfMoneyIsInMinSum() {
        if (requestedAmountOfMoney % minAvailableSum > 0) {
            throw new IllegalArgumentException(String.format("Сумма для снятия должна быть кратна: %d", minAvailableSum));
        }
    }

    private void minAvailableSum() {
        if (requestedAmountOfMoney < minAvailableSum) {
            throw new IllegalArgumentException(String.format("Минимальная допустимая сумма для снятия: %d. Сумма запрошенная: %d", minAvailableSum, requestedAmountOfMoney));
        }
    }

    private boolean isPossibleGiveAmount(RequestOfMoney requestOfMoney) {
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
