package homework;

import homework.atm.Atm;
import homework.atm.Bill;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Bill> generateBills = generateBills(1, 1, 10);
        Atm atm = new Atm();
        atm.printCurrentAmount();
        atm.takeMoney(generateBills);
        int amount = 2300;
        System.out.println("Запрошенная сумма: " + amount);
        List<Bill> giveMoney = atm.takeRequest(amount);
        System.out.println("Количество выданных купюр: " + giveMoney.size());
    }

    private static List<Bill> generateBills(int countOfThousand, int countOfFiveHundred, int countOfOneHundred) {
        List<Bill> billList = new ArrayList<>();
        for (int i = 0; i < countOfThousand; i++) {
            billList.add(Bill.ONE_THOUSAND);
        }

        for (int i = 0; i < countOfFiveHundred; i++) {
            billList.add(Bill.FIVE_HUNDRED);
        }

        for (int i = 0; i < countOfOneHundred; i++) {
            billList.add(Bill.ONE_HUNDRED);
        }
        return billList;
    }
}
