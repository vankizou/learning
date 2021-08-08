package designpattern.结构型模式.装饰器模式;

/**
 * 具体组件实现
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 10:50
 **/
public class ComponentImpl implements Component {
    public ComponentImpl() {
        System.out.println("创建具体组件...");
    }

    @Override
    public void operation() {
        System.out.println("具体组件的方法operation()");
    }
}
