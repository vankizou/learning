package com.zoufanqi.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 主程序
 *
 * @author: ZOUFANQI
 * @create: 2021-09-02 17:14
 **/
@ComponentScan("com.zoufanqi.ioc")
public class MainCase {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainCase.class);

        MyServiceSingleton myServiceSingleton = context.getBean(MyServiceSingleton.class);
        System.out.println("======= 第一次获取singleton对象 =======");
        System.out.println(myServiceSingleton);
        System.out.println(myServiceSingleton.a++);
        System.out.println(myServiceSingleton.myServiceSingleton2);
        System.out.println(myServiceSingleton.myServicePrototype2);
        System.out.println(myServiceSingleton.myServiceSingleton2.b++);
        System.out.println(myServiceSingleton.myServicePrototype2.b++);
        System.out.println();

        MyServiceSingleton myServiceSingleton2 = context.getBean(MyServiceSingleton.class);
        System.out.println("======= 第二次获取singleton对象 =======");
        System.out.println(myServiceSingleton2);        // 对象没变
        System.out.println(myServiceSingleton2.a++);    // 值+1
        System.out.println(myServiceSingleton2.myServiceSingleton2);    // 对象没变
        System.out.println(myServiceSingleton2.myServicePrototype2);    // 对象没变
        System.out.println(myServiceSingleton2.myServiceSingleton2.b++);// 值+1
        System.out.println(myServiceSingleton2.myServicePrototype2.b++);// 值+1
        System.out.println();

        MyServicePrototype myServicePrototype = context.getBean(MyServicePrototype.class);
        System.out.println("======= 第一次获取prototype对象 =======");
        System.out.println(myServicePrototype);
        System.out.println(myServicePrototype.a++);
        System.out.println(myServicePrototype.myServiceSingleton2);
        System.out.println(myServicePrototype.myServicePrototype2);
        System.out.println(myServicePrototype.myServiceSingleton2.b++);
        System.out.println(myServicePrototype.myServicePrototype2.b++);
        System.out.println();

        MyServicePrototype myServicePrototype2 = context.getBean(MyServicePrototype.class);
        System.out.println("======= 第二次获取prototype对象 =======");
        System.out.println(myServicePrototype2);        // 对象改变
        System.out.println(myServicePrototype2.a++);    // 值没变
        System.out.println(myServicePrototype2.myServiceSingleton2);    // 对象没变
        System.out.println(myServicePrototype2.myServicePrototype2);    // 对象改变
        System.out.println(myServicePrototype2.myServiceSingleton2.b++);// 值+1
        System.out.println(myServicePrototype2.myServicePrototype2.b++);// 值没变
        System.out.println();


    }
}
