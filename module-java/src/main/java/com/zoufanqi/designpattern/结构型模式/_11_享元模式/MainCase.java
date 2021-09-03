package com.zoufanqi.designpattern.结构型模式._11_享元模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-07 11:28
 **/
public class MainCase {
    public static void main(String[] args) {
        Flyweight fa1 = FlyweightFactory.getFlyweight("a");
        Flyweight fa2 = FlyweightFactory.getFlyweight("a");
        Flyweight fa3 = FlyweightFactory.getFlyweight("a");

        Flyweight fb1 = FlyweightFactory.getFlyweight("b");
        Flyweight fb2 = FlyweightFactory.getFlyweight("b");

        fa1.operation(new UnFlyweight("第1次调用a"));
        fa2.operation(new UnFlyweight("第2次调用a"));
        fa3.operation(new UnFlyweight("第3次调用a"));

        fb1.operation(new UnFlyweight("第1次调用b"));
        fb2.operation(new UnFlyweight("第2次调用b"));
    }
}
