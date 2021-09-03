package com.zoufanqi.classloader;

/**
 * 自字义两个类加载器都加载该类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-22 00:40
 **/
public class ObjCase {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        // 父/子 自定义类加载器
        MyClassLoaderFather fatherLoader = new MyClassLoaderFather();
        MyClassLoaderSun sunLoader = new MyClassLoaderSun();

        // 加载同一个class
        Class<?> fatherClz = fatherLoader.loadClass("com.zoufanqi.classloader.ObjCase");
        Class<?> sunClz = sunLoader.loadClass("com.zoufanqi.classloader.ObjCase");

        // 生成的Class类的类加载器是不一样的
        System.out.println(fatherClz.getClassLoader());
        System.out.println(sunClz.getClassLoader());

        // 都可以实例对象
        Object fatherLoaderObj = fatherClz.newInstance();
        Object sunLoaderObj = sunClz.newInstance();

        System.out.println(fatherLoaderObj);
        System.out.println(sunLoaderObj);
    }

}
