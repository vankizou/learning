package com.zoufanqi.ioclifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
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
        EnvironmentAware,
        ApplicationContextAware,
        BeanPostProcessor,
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
        System.out.println("====================================");
        System.out.println("======== 获取bean：" + context.getBean(LifecycleMainCase.class));
        System.out.println();
        System.out.println("======== 获取bean：" + context.getBean("我是这个bean的名称"));
        System.out.println();
        System.out.println("=========== bean的销毁 ==========");
        context.close();
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

    /**
     * prototype时会调用
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化方法：BeanPostProcessor.postProcessBeforeInitialization - " + beanName + " - " + bean);
        return bean;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("初始化方法：@PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化方法：InitializingBean.afterPropertiesSet");
    }

    /**
     * prototype时会调用
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化方法：BeanPostProcessor.postProcessAfterInitialization - " + beanName + " - " + bean);
        return bean;
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("销毁方法：@PreDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("销毁方法：DisposableBean.destroy()");
    }
}
