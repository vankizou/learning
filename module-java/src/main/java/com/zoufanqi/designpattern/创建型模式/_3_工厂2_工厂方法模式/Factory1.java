package com.zoufanqi.designpattern.创建型模式._3_工厂2_工厂方法模式;

/**
 * 我是生产Product1的工厂
 *
 * @author: ZOUFANQI
 * @create: 2021-08-05 18:06
 **/
public class Factory1 implements Factory {
    @Override
    public Product createProduct() {
        System.out.println("工厂方法：我生产了一件Product1产品，我很棒！");
        return new Product1();
    }
}
