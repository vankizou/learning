package com.zoufanqi.designpattern.创建型模式._3_工厂2_工厂方法模式;

/**
 * 产品1
 *
 * @author: ZOUFANQI
 * @create: 2021-08-05 18:04
 **/
public class Product1 implements Product {

    @Override
    public void show() {
        System.out.println("工厂方法：我是产品1，我能跑能滑~");
    }
}
