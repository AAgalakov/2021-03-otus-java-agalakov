package homework;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyIoc {

    private static final String NAME_OF_CLASS = LoggingImpl.class.getCanonicalName();
    private static final Class<? extends Annotation> ANNOTATION = Log.class;

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
            Method[] methods = platformClassLoader.loadClass(NAME_OF_CLASS).getMethods();
            for (Method classMethod : methods) {
                if (classMethod.getName().equals(method.getName())) {
                    method = classMethod;
                    break;
                }
            }
            if (method.isAnnotationPresent(ANNOTATION)) {
                printLog(method, args);
            }
            return method.invoke(myClass, args);
        }

        private void printLog(Method method, Object[] args) {
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
    }
}
