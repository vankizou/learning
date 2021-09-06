package com.zoufanqi.ioclifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

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
        EnvironmentAware,
        ApplicationContextAware,
        InitializingBean,
        DisposableBean {

    public Lifecycle2Bean() {
        System.out.println("初始化方法：实例构造...");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("初始化方法：BeanNameAware.setBeanName - " + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("初始化方法：BeanFactoryAware.setBeanFactory - " + beanFactory);
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("初始化方法：EnvironmentAware.setEnvironment - " + environment);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("初始化方法：ApplicationContextAware.setApplicationContext - " + applicationContext);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("初始化方法：@PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化方法：InitializingBean.afterPropertiesSet");
    }

    public void myInitMethod() {
        System.out.println("初始化方法：自定义initMethod");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("销毁方法：@PreDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("销毁方法：DisposableBean.destroy");
    }

    public void myDestroyMethod() {
        System.out.println("销毁方法：自定义DestroyMethod");
    }
}
