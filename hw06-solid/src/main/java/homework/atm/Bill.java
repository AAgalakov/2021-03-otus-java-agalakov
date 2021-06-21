package homework.atm;

public enum Bill {
    ONE_THOUSAND(1000),
    FIVE_HUNDRED(500),
    ONE_HUNDRED(100);

    private final int denomination;

    Bill(int denomination) {
        this.denomination = denomination;
    }

    public static int getDenomination(Bill bill) {
        return bill.denomination;
    }
}
