package com.zoufanqi.designpattern.创建型模式._4_建造者模式;

/**
 * 具体的建造者
 *
 * @author: ZOUFANQI
 * @create: 2021-08-05 19:02
 **/
public class Builder1 extends Builder {
    @Override
    public void buildPartA() {
        product.setPartA("建造PartA");
    }

    @Override
    public void buildPartB() {
        product.setPartA("建造PartB");
    }

    @Override
    public void buildPartC() {
        product.setPartA("建造PartC");
    }
}
