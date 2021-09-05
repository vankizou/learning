package com.zoufanqi.transaction;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 主程序
 *
 * @author: ZOUFANQI
 * @create: 2021-09-04 09:14
 **/
@ComponentScan({"com.zoufanqi.transaction"})
public class MainCase {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainCase.class);

        MyService myService = context.getBean(MyService.class);
//        myService.requireExceptionTest1();
        myService.requireExceptionTest2();
//        myService.requireNewExceptionTest1();
//        myService.requireNewExceptionTest2();

    }
}
