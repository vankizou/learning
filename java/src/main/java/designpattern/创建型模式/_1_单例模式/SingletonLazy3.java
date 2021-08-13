package designpattern.创建型模式._1_单例模式;

/**
 * 懒汉式
 *
 * @author: ZOUFANQI
 * @create: 2021-08-04 17:42
 **/
public class SingletonLazy3 {
    private static SingletonLazy3 instance = null;

    private SingletonLazy3() {
    }

    public static synchronized SingletonLazy3 getInstance() {
        if (instance == null) {
            instance = new SingletonLazy3();
        }
        return instance;
    }
}
