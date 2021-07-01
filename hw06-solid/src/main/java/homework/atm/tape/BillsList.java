package homework.atm.tape;

import homework.atm.Bill;
import homework.atm.service.BundleOfBanknotes;

import java.util.List;
import java.util.Map;

public interface BillsList {

    int putBills(List<Bill> billList);

    List<Bill> takeBills(BundleOfBanknotes bundleOfBanknotes);

    int giveCountOfBills(Bill bill);

    int giveMinAvailableSum();

    Map<Bill, Integer> getCountOfBillMap();
}
