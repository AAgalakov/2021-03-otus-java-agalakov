package homework;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MyIoc {

    private static final String NAME_OF_CLASS = LoggingImpl.class.getCanonicalName();
    private static final Class<? extends Annotation> ANNOTATION = Log.class;

    private MyIoc() {
    }

    static Logging createMyClass() throws ClassNotFoundException {
        InvocationHandler handler = new DemoInvocationHandler(new LoggingImpl());
        return (Logging) Proxy.newProxyInstance(MyIoc.class.getClassLoader(),
                new Class<?>[]{Logging.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final Logging myClass;
        private final Set<String> methodsNamesSet;

        DemoInvocationHandler(Logging myClass) throws ClassNotFoundException {
            ClassLoader platformClassLoader = ClassLoader.getSystemClassLoader();
            Method[] methods = platformClassLoader.loadClass(NAME_OF_CLASS).getMethods();
            methodsNamesSet = Arrays.stream(methods)
                    .filter(method -> method.isAnnotationPresent(ANNOTATION))
                    .map(Method::getName)
                    .collect(Collectors.toSet());
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            if (methodsNamesSet.contains(methodName)) {
                printLog(methodName, args);
            }
            return method.invoke(myClass, args);
        }

        private void printLog(String methodName, Object[] args) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("executed methodName: ")
                    .append(methodName)
                    .append(";\n")
                    .append("param: ");
            for (int i = 0; i < args.length; i++) {
                stringBuilder.append(args[i]);
                if (i != args.length - 1) {
                    stringBuilder.append(", ");
                }
            }
            System.out.println(stringBuilder);
        }
    }
}