package homework;

public class LoggingImpl implements Logging{

    @Log
    @Override
    public int calculating(int a, int b){
        return a + b;
    }

    @Log
    @Override
    public String someMethod(int a, int b, String str) {
        return str + " " + calculating(a, b);
    }
}
