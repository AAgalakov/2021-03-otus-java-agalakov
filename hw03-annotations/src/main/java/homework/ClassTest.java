package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ClassTest {

    private static final String BEFORE_TEST_ANNOTATION_NAME = Before.class.getSimpleName();
    private static final String TEST_ANNOTATION_NAME = Test.class.getSimpleName();
    private static final String AFTER_TEST_ANNOTATION_NAME = After.class.getSimpleName();
    private static final String ERROR_MESSAGE = "Во время тестирования произошла ошибка";

    public static void testClass(String className) throws ClassNotFoundException, NoSuchMethodException{
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor();

        Map<String, List<Method>> methodForTest = getMethodForTest(clazz);

        String resultOfTest = null;
        try {
             resultOfTest = testing(constructor, methodForTest);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        System.out.println(Optional.ofNullable(resultOfTest).orElse(ERROR_MESSAGE));
    }

    private static Map<String, List<Method>> getMethodForTest(Class<?> clazz){
        Method[] methods = clazz.getMethods();

        return Map.of(BEFORE_TEST_ANNOTATION_NAME, getMethodList(methods, Before.class),
                TEST_ANNOTATION_NAME, getMethodList(methods, Test.class),
                AFTER_TEST_ANNOTATION_NAME, getMethodList(methods, After.class));
    }

    private static String testing(Constructor<?> constructor,
                                Map<String, List<Method>> methodsMap) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<String> success = new HashSet<>();
        Set<String> failedTests = new HashSet<>();
        List<Method> methodTestList = methodsMap.get(TEST_ANNOTATION_NAME);
        for (Method value : methodTestList) {
            Object newInstance = constructor.newInstance();
            for (Method method : methodsMap.get(BEFORE_TEST_ANNOTATION_NAME)) {
                method.invoke(newInstance);
            }
            try {
                value.invoke(newInstance);
            } catch (Exception e) {
                failedTests.add(value.getName());
            }
            for (Method method : methodsMap.get(AFTER_TEST_ANNOTATION_NAME)) {
                method.invoke(newInstance);
            }
            success.add(value.getName());
        }

        return printResults(methodTestList, success, failedTests);
    }

    private static String printResults(List<Method> methodTestList,
                                     Set<String> doneTest,
                                     Set<String> failTests) {
        int countOfFailedTests = failTests.size();
        int countOfSuccessTests = doneTest.size() - countOfFailedTests;
        StringBuilder successTests = new StringBuilder();
        for (String value : doneTest){
            if (!failTests.contains(value)){
                successTests.append(value);
                successTests.append(" ");
            }
        }
        StringBuilder failedTests = new StringBuilder();
        for (String value : failTests){
            failedTests.append(value);
            failedTests.append(" ");
        }

        return String.format("Всего прошло тестов: %d. \n" +
                "Прошло успешно: %d - %s \n" +
                "Не прошло: %d - %s",
                methodTestList.size(), countOfSuccessTests,
                successTests, countOfFailedTests, failedTests);
    }

    private static List<Method> getMethodList(Method[] methods, Class<? extends Annotation> clazz) {
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(clazz))
                .collect(Collectors.toList());
    }
}