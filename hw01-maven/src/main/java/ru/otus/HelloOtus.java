package ru.otus;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class HelloOtus {
    public static void main(final String[] args) {
        final Map<String, Integer> items = ImmutableMap.of("coin", 3, "glass", 4, "pencil", 1);

        items.entrySet()
             .forEach(System.out::println);

        final List<String> fruits = Lists.newArrayList("orange", "banana", "kiwi",
                "mandarin", "date", "quince");

        for (final String fruit: fruits) {
            System.out.println(fruit);
        }
    }
}