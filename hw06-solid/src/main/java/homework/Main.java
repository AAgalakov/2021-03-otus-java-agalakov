package homework;

import homework.atm.Atm;
import homework.atm.Bill;
import homework.atm.tape.TapeOfBills;
import homework.atm.tape.TapeOfBillsOfBillsImpl;
import homework.atm.service.TapeOfBillsService;
import homework.atm.service.TapeOfBillsServiceImpl;
import homework.atm.validation.Validator;
import homework.atm.validation.ValidatorImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<Bill> generateBills = generateBills(3);
        TapeOfBills tapeOfBills = new TapeOfBillsOfBillsImpl();
        TapeOfBillsService tapeOfBillsService = new TapeOfBillsServiceImpl(tapeOfBills);
        Validator validator = new ValidatorImpl();
        Atm atm = new Atm(validator, tapeOfBillsService);
        atm.printCurrentAmount();
        atm.pushMoney(generateBills);
        atm.printCurrentAmount();
        int amount = 100;
        System.out.println("Запрошенная сумма: " + amount);
        List<Bill> giveMoney = atm.pullMoney(amount);
        System.out.println("Количество выданных купюр: " + giveMoney.size());
        atm.printCurrentAmount();
    }

    private static Queue<Bill> generateBills(int countOfBill) {
        Bill[] values = Bill.values();
        Queue<Bill> billList = new LinkedList<>();
        for (Bill bill : values) {
            for (int i = 0; i < countOfBill; i++) {
                billList.add(bill);
            }
        }
        return billList;
    }
}
