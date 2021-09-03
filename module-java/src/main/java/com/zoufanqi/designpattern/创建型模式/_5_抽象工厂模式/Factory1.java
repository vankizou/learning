package com.zoufanqi.designpattern.创建型模式._5_抽象工厂模式;

/**
 * 实体工厂，可生产不同产品
 *
 * @author: ZOUFANQI
 * @create: 2021-08-05 18:36
 **/
public class Factory1 implements AbstractFactory {
    @Override
    public Product1 createProduct1() {
        System.out.println("抽象工厂：无敌工厂生产了Product1!");
        return new Product1();
    }

    @Override
    public Product2 createProduct2() {
        System.out.println("抽象工厂：无敌工厂生产了Product2~~");
        return new Product2();
    }
}
