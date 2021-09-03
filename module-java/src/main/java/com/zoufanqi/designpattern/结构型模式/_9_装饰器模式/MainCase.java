package com.zoufanqi.designpattern.结构型模式._9_装饰器模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-07 10:57
 **/
public class MainCase {
    public static void main(String[] args) {
        Component component = new ComponentImpl();
        component.operation();
        System.out.println("=============================");
        Decorator decorator = new DecoratorImpl(component);
        decorator.operation();
    }
}
