package homework;

import java.util.LinkedList;
import java.util.List;

public class Homework {
    public static void main(String[] args) {
        List<Object[]> integerList = new LinkedList<>();

        for (int j = 1; j > 0; j++) {
            integerList.add(new Object[100]);
            if ((j % 2) == 0) {
                integerList.remove(0);
            }
        }
    }
}
