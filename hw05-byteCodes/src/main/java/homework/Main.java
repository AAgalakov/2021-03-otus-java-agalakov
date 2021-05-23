package homework;

public class Main {
    public static void main(String[] args) {
        Logging testLogging = MyIoc.createMyClass();
        System.out.println(testLogging.calculating(2, 7));
        System.out.println(testLogging.someMethod(5, 4, "Сложение"));
    }
}
