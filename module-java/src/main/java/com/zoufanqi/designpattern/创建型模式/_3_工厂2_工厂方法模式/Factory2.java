package com.zoufanqi.designpattern.创建型模式._3_工厂2_工厂方法模式;

/**
 * 我是生产Product2的工厂
 *
 * @author: ZOUFANQI
 * @create: 2021-08-05 18:06
 **/
public class Factory2 implements Factory {
    @Override
    public Product createProduct() {
        System.out.println("工厂方法：我生产了一件Product2产品，我很优秀！");
        return new Product2();
    }
}
