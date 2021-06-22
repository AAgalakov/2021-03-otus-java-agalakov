package homework;

import homework.atm.*;
import homework.bill.Bill;
import homework.bill.Tape;
import homework.bill.TapeOfBills;
import homework.validate.Valid;
import homework.validate.Validator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<Bill> generateBills = generateBills(3);
        Tape tape = new TapeOfBills();
        Valid valid = new Validator();
        Atm atm = new Atm(tape, valid);
        atm.printCurrentAmount();
        atm.pushMoney(generateBills);
        int amount = 2300;
        System.out.println("Запрошенная сумма: " + amount);
        List<Bill> giveMoney = atm.pullMoney(amount);
        System.out.println("Количество выданных купюр: " + giveMoney.size());
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
