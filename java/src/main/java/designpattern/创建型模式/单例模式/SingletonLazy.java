package designpattern.创建型模式.单例模式;

/**
 * 懒汉式
 *
 * @author: ZOUFANQI
 * @create: 2021-08-04 17:42
 **/
public class SingletonLazy {
    private static SingletonLazy instance = null;

    private SingletonLazy() {

    }

    /**
     * 会频繁加锁
     *
     * @return
     */
    public static synchronized SingletonLazy getInstance() {
        if (instance == null) {
            instance = new SingletonLazy();
        }
        return instance;
    }
}
