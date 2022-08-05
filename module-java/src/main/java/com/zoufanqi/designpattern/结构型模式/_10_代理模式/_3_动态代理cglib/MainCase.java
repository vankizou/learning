package com.zoufanqi.designpattern.结构型模式._10_代理模式._3_动态代理cglib;

/**
 * cglib动态代理
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 15:15
 **/
public class MainCase {
    /*
        需要添加依赖：
        <dependency>
            <groupId>org.ow2.asm</groupId>
            <artifactId>asm</artifactId>
            <version>9.2</version>
        </dependency>
        <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib</artifactId>
            <version>3.3.0</version>
        </dependency>
     */
    public static void main(String[] args) {
        // 代理实例
//        Subject proxyIns1 = new ProxyHandler().getProxyIns(RealSubject.class);
//        System.out.println(proxyIns1.request());

        RealSubject proxyIns2 = new ProxyHandler().getProxyIns(RealSubject.class);
        System.out.println(proxyIns2.request());
    }
}
