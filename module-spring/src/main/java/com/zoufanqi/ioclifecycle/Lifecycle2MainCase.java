package com.zoufanqi.ioclifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 主程序 - bean的生命周期
 *
 * @author: ZOUFANQI
 * @create: 2021-09-02 17:14
 **/
public class Lifecycle2MainCase {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Lifecycle2MainCase.class);
        System.out.println("======== 获取bean：" + context.getBean(Lifecycle2Bean.class));
        System.out.println();
        System.out.println("======== 获取bean：" + context.getBean("name1"));
        System.out.println("======== 获取bean：" + context.getBean("name2"));
        System.out.println("=========== bean的销毁 ==========");
        context.close();
    }

    @Bean(initMethod = "myInitMethod", destroyMethod = "myDestroyMethod", name = {"name1", "name2"})
    public Lifecycle2Bean lifecycle2() {
        System.out.println("------------ @Bean方法");
        return new Lifecycle2Bean();
    }
}
