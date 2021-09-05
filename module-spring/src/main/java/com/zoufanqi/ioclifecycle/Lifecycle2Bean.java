package com.zoufanqi.ioclifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 主程序 - bean的生命周期
 *
 * @author: ZOUFANQI
 * @create: 2021-09-02 17:14
 **/
@Configuration
public class Lifecycle2Bean implements
        BeanNameAware,
        BeanFactoryAware,
        ApplicationContextAware,
        InitializingBean,
        DisposableBean {

    public Lifecycle2Bean() {
        System.out.println("初始化方法：实例构造...");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("初始化方法：setBeanName: " + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("初始化方法：setBeanFactory: " + beanFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("初始化方法：setApplicationContext: " + applicationContext);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("初始化方法：postConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化方法：afterPropertiesSet");
    }

    public void myInitMethod() {
        System.out.println("初始化方法：自定义initMethod");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("销毁方法：preDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("销毁方法：destroy");
    }

    public void myDestroyMethod() {
        System.out.println("销毁方法：自定义DestroyMethod");
    }
}
