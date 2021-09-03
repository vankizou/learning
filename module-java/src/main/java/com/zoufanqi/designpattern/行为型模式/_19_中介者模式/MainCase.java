package com.zoufanqi.designpattern.行为型模式._19_中介者模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:23
 **/
public class MainCase {
    public static void main(String[] args) {
        Mediator mediator = new MediatorImpl();
        Colleague ca = new ColleagueImplA();
        Colleague cb = new ColleagueImplB();

        mediator.register(ca);
        mediator.register(cb);

        ca.send();
        System.out.println("--------------");
        cb.send();
    }
}
