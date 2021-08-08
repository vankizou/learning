package designpattern.结构型模式.享元模式;

/**
 * 享元具体实现
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 11:22
 **/
public class FlyweightImpl implements Flyweight {
    private String key;

    public FlyweightImpl(String key) {
        this.key = key;
        System.out.println("具体享元" + key + "被创建！");
    }

    @Override
    public void operation(UnFlyweight unFlyweight) {
        System.out.println("具体享元" + key + "被调用");
        System.out.println("非享元信息是：" + unFlyweight.getInfo());
    }
}
