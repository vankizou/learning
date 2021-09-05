package com.zoufanqi.ioclifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 主程序 - bean的生命周期
 *
 * @author: ZOUFANQI
 * @create: 2021-09-02 17:14
 **/
@Component("我是这个bean的名称")
//@Scope("prototype")
public class LifecycleMainCase implements
        BeanNameAware,
        BeanFactoryAware,
        ApplicationContextAware,
        InitializingBean,
        DisposableBean {

    public LifecycleMainCase() {
        System.out.println("初始化方法：实例构造...");
    }

    /**
     * 直接执行主类
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LifecycleMainCase.class);
        System.out.println("======== 获取bean：" + context.getBean(LifecycleMainCase.class));
        System.out.println();
        System.out.println("======== 获取bean：" + context.getBean("我是这个bean的名称"));
        System.out.println();
        System.out.println("=========== bean的销毁 ==========");
        context.close();
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

    @PreDestroy
    public void preDestroy() {
        System.out.println("销毁方法：preDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("销毁方法：destroy");
    }
}
