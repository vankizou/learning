package designpattern.创建型模式.单例模式;

/**
 * 饿汉式
 *
 * @author: ZOUFANQI
 * @create: 2021-08-04 17:42
 **/
public class SingletonHungry {
    private static SingletonHungry instance = new SingletonHungry();

    private SingletonHungry() {
    }

    public static synchronized SingletonHungry getInstance() {
        return instance;
    }
}
