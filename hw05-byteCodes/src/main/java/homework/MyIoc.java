package homework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyIoc {

    private MyIoc() {
    }

    static Logging createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new LoggingImpl());
        return (Logging) Proxy.newProxyInstance(MyIoc.class.getClassLoader(),
                new Class<?>[]{Logging.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final Logging myClass;

        DemoInvocationHandler(Logging myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            ClassLoader platformClassLoader = ClassLoader.getSystemClassLoader();
            Method[] methods = platformClassLoader.loadClass("homework.LoggingImpl").getMethods();
            for (Method classMethod: methods) {
                if (classMethod.getName().equals(method.getName())){
                    method = classMethod;
                    break;
                }
            }
            if (method.isAnnotationPresent(Log.class)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("executed method: ")
                        .append(method.getName())
                        .append(", ")
                        .append("param: ");
                for (int i = 0; i < args.length; i++) {
                    stringBuilder.append(args[i]);
                    if (i < args.length - 1) {
                        stringBuilder.append(", ");
                    }
                }
                System.out.println(stringBuilder);
            }
            return method.invoke(myClass, args);
        }
    }
}
