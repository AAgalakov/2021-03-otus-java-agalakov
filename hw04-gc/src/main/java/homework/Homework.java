package homework;

import java.util.ArrayList;
import java.util.List;

public class Homework {
    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();

        while (true) {
            integerList.addAll(getCollection());
            integerList = new ArrayList<>(integerList);
        }
    }

    private static List<Integer> getCollection() {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            integers.add(i);
        }
        return integers;
    }
}
