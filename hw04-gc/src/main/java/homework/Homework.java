package homework;

import java.util.ArrayList;
import java.util.List;

public class Homework {
    public static void main(String[] args) {
        int i = 0;
        List<Integer> integerList = new ArrayList<>();
        List<Integer> collection = getCollection();

        while (true) {
            i++;
            integerList.addAll(collection);
            if (i % 2 == 0) {
                integerList.remove(0);
            }
            System.out.println(integerList.size());
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
