package com.zoufanqi.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载 - 打破双亲委派（子类加载）
 *
 * @author: ZOUFANQI
 * @create: 2021-08-21 23:49
 **/
public class MyClassLoaderSun extends MyClassLoaderFather {

    /**
     * 打破双亲委派必须重写loadClass
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            // 获取编译后的class
            String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
            // 从class中读取字节数组
            InputStream is = super.getClass().getResourceAsStream(fileName);
            if (is == null) {
                return super.loadClass(name);
            }
            byte[] b = new byte[is.available()];
            is.read(b);
            // 使用父类的方法将字节数组转换为class
            return super.defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }
}
