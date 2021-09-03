package com.zoufanqi.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: ZOUFANQI
 * @create: 2021-09-02 17:14
 **/
@ComponentScan("com.zoufanqi.aop")
@EnableAspectJAutoProxy
public class MainCase {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainCase.class);

        MyService myService = context.getBean(MyService.class);
        myService.doSomething();

        MyService2 myService2 = context.getBean(MyService2.class);
        myService2.doSomething();
    }
}
