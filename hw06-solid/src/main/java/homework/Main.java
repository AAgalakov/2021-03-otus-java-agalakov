package homework;

import homework.atm.Atm;
import homework.atm.Bill;
import homework.atm.TapeOfBills;
import homework.atm.Validator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<Bill> generateBills = generateBills(3);
        Atm atm = new Atm(new TapeOfBills(), new Validator());
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
        for (Bill bill: values){
            for (int i = 0; i < countOfBill; i++){
                billList.add(bill);
            }
        }
        return billList;
    }
}
