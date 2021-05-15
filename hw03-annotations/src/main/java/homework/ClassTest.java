package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ClassTest<T> {

    public void doTest(Class<T> clazz) throws NoSuchMethodException {
        List<Method> methodBeforeList = Arrays.stream(ClassForTest.class.getMethods())
                .filter(method -> method.isAnnotationPresent(Before.class))
                .collect(Collectors.toList());
        List<Method> methodAfterList = Arrays.stream(ClassForTest.class.getMethods())
                .filter(method -> method.isAnnotationPresent(After.class))
                .collect(Collectors.toList());
        List<Method> methodTestList = Arrays.stream(ClassForTest.class.getMethods())
                .filter(method -> method.isAnnotationPresent(Test.class))
                .collect(Collectors.toList());

        Constructor<T> constructor = clazz.getConstructor();

        AtomicInteger exceptions = new AtomicInteger();
        AtomicInteger doneTests = new AtomicInteger();
        Set<String> doneTest = new HashSet<>();
        Set<String> failTests = new HashSet<>();

        methodTestList.forEach(method -> {
            T instance = null;
            try {
                instance = constructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T finalMyClass = instance;
            if (instance == null){
                return;
            }
            methodBeforeList.forEach(method1 -> {
                try {
                    method1.invoke(finalMyClass);
                    System.out.println(finalMyClass.hashCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            try {
                method.invoke(instance);
                System.out.println(finalMyClass.hashCode());
                doneTests.getAndIncrement();
            } catch (Exception e) {
                exceptions.getAndIncrement();
                Arrays.stream(e.getStackTrace()).forEach(System.out::println);
                failTests.add(method.getAnnotation(Test.class).value());
            }
            T finalMyClass1 = instance;
            methodAfterList.forEach(method1 -> {
                try {
                    method1.invoke(finalMyClass1);
                    System.out.println(finalMyClass.hashCode());
                    doneTest.add(method.getAnnotation(Test.class).value());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            System.out.println();
        });
        System.out.println("Всего прошло тестов: " + methodTestList.size());
        System.out.println("Прошли успешно: " + doneTests);
        doneTest.stream().filter(s -> !failTests.contains(s)).forEach(System.out::println);
        System.out.println("Не прошли: " + exceptions);
        failTests.forEach(System.out::println);
    }
}
