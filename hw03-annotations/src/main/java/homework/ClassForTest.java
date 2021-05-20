package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

public class ClassForTest {

    @Before
    public void annotationBefore(){
        System.out.println("Hello from before");
    }

    @After
    public void annotationAfter(){
        System.out.println("Hello from after");
    }

    @Test("Первый тест")
    public void methodTest1(){
        System.out.println("Hello from methodTest1");
    }

    @Test("Второй тест")
    public void methodTest2(){
        System.out.println("Hello from methodTest2");
    }

    @Test("Третий тест")
    public void methodTest3() {
        System.out.println("Hello from methodTest3");
        throw new RuntimeException("Exception");
    }
}
