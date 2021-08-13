package designpattern.创建型模式._1_单例模式;

/**
 * 【推荐】
 * 懒汉式（延迟初始化占位类模式）
 *
 * @author: ZOUFANQI
 * @create: 2021-08-13 10:20
 **/
public class SingletonLazy {
    private SingletonLazy() {
        System.out.println("类初始化完成！");
    }

    public static SingletonLazy getInstance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder {
        private static SingletonLazy instance = new SingletonLazy();
    }

}
