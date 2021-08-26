package designpattern.创建型模式._4_建造者模式;

/**
 * 抽象建造者：包含创建产品各个子部件的抽象方法
 *
 * @author: ZOUFANQI
 * @create: 2021-08-05 19:00
 **/
public abstract class Builder {
    // 创建产口对象
    protected Product product = new Product();

    public abstract void buildPartA();

    public abstract void buildPartB();

    public abstract void buildPartC();

    // 返回产品对象
    public Product getProduct() {
        return product;
    }
}
