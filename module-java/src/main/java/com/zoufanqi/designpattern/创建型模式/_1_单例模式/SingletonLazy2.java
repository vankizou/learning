package com.zoufanqi.designpattern.创建型模式._1_单例模式;

/**
 * 懒汉式（双重检查）
 *
 * @author: ZOUFANQI
 * @create: 2021-08-04 17:42
 **/
public class SingletonLazy2 {
    // 防止对象初始化时指令重排，必须要加volatile
    private static volatile SingletonLazy2 instance = null;

    private SingletonLazy2() {
    }

    public static SingletonLazy2 getInstance() {
        if (instance == null) {
            synchronized (SingletonLazy2.class) {
                /**
                 * 线程队列WaitSet中后续线程获取到CPU时间分片进入到同步块，此时instance可能已经初始化了
                 */
                if (instance == null) {
                    instance = new SingletonLazy2();
                }
            }
        }
        return instance;
    }
}
