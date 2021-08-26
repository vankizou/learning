package designpattern.行为型模式._13_模板方法模式;

/**
 * 抽象类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 11:26
 **/
public abstract class AbstractClass {
    /**
     * 模板方法
     */
    public void templateMethod() {
        this.specificMethod();
        this.abstractMethod1();
        this.abstractMethod2();
    }

    /**
     * 抽象类具体方法
     */
    public void specificMethod() {
        System.out.println("抽象类中的具体方法被调用……");
    }

    /**
     * 抽象方法1
     */
    public abstract void abstractMethod1();

    /**
     * 抽象方法2
     */
    public abstract void abstractMethod2();
}
