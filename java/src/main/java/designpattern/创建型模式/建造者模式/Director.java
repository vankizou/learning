package designpattern.创建型模式.建造者模式;

/**
 * 指挥者：调用建造者中的方法完成复杂对象的创建
 *
 * @author: ZOUFANQI
 * @create: 2021-08-05 19:04
 **/
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    /**
     * 产品构建与组装
     *
     * @return
     */
    public Product construct() {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getProduct();
    }
}
