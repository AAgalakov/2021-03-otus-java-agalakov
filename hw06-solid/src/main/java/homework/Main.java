package homework;

import homework.atm.Atm;
import homework.atm.AtmImpl;
import homework.atm.Bill;
import homework.atm.service.BillsListService;
import homework.atm.service.BillsListServiceImpl;
import homework.atm.tape.BillsList;
import homework.atm.tape.BillsListImpl;
import homework.atm.validation.Validator;
import homework.atm.validation.ValidatorImpl;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Bill> generateBills = generateBills(3);
        BillsList billsList = new BillsListImpl();
        BillsListService billsListService = new BillsListServiceImpl(billsList);
        Validator validator = new ValidatorImpl();
        Atm atm = new AtmImpl(validator, billsListService);
        atm.printCurrentAmount();
        atm.pushMoney(generateBills);
        atm.printCurrentAmount();
        int amount = 7800;
        System.out.println("Запрошенная сумма: " + amount);
        List<Bill> giveMoney = atm.pullMoney(amount);
        System.out.println("Количество выданных купюр: " + giveMoney.size());
        atm.printCurrentAmount();
    }

    private static List<Bill> generateBills(int countOfBill) {
        Bill[] values = Bill.values();
        List<Bill> billList = new LinkedList<>();
        for (Bill bill : values) {
            for (int i = 0; i < countOfBill; i++) {
                billList.add(bill);
            }
        }
        return billList;
    }
}
