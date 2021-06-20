package homework;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Logging testLogging = MyIoc.createMyClass();
        System.out.println(testLogging.calculating(5, 7));
        System.out.println(testLogging.someMethod(5, 4, "Сложение"));
    }
}
