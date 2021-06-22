package homework.validate;

import homework.bill.CountOfBills;
import homework.bill.Tape;

public interface Valid {

    CountOfBills validate(Tape tapeOfBills, int requestedAmountOfMoney);
}
