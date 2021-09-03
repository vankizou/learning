package com.zoufanqi.designpattern.创建型模式._3_工厂2_工厂方法模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-05 18:08
 **/
public class MainTest {

    public static void main(String[] args) {
        // 真实业务场景中工厂一般为单例
        new Factory1().createProduct().show();
        new Factory2().createProduct().show();
    }

}
