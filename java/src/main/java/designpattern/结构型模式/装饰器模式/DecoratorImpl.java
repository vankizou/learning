package designpattern.结构型模式.装饰器模式;

/**
 * 具体装饰角色实现
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 10:56
 **/
public class DecoratorImpl extends Decorator {

    public DecoratorImpl(Component component) {
        super(component);
    }

    public void operation() {
        super.operation();
        System.out.println("具体装饰角色添加了一张桌子……");
    }
}
