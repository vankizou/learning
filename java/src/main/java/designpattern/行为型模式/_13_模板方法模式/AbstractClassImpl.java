package designpattern.行为型模式._13_模板方法模式;

/**
 * 具体子类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 11:29
 **/
public class AbstractClassImpl extends AbstractClass {
    @Override
    public void abstractMethod1() {
        System.out.println("子类实现的抽象方法1被调用……");
    }

    @Override
    public void abstractMethod2() {
        System.out.println("子类实现的抽象方法2被调用……");
    }
}
