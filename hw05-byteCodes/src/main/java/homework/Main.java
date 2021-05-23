package homework;

public class Main {
    public static void main(String[] args) {
        Logging testLogging = MyIoc.createMyClass();
        testLogging.calculating(2, 7);
        testLogging.someMethod(5, 4, "Сложение");
    }
}
