package com.zoufanqi.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 配置AOP
 *
 * @author: ZOUFANQI
 * @create: 2021-09-02 17:12
 **/
@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(public * com.zoufanqi.aop.MyService.*(..))")
    public void pc1() {
        System.out.println("---这个方法内容是不会执行的---");
    }

    @Before("pc1()")
    public void before1() {
        System.out.println("=======代理方法：before=======");
    }

    @After("pc1()")
    public void after1() {
        System.out.println("=======代理方法：after=======");
    }

    @Pointcut("execution(public * com.zoufanqi.aop.MyService2.*(..))")
    public void pc2() {
        System.out.println("---这个方法内容是不会执行的---");
    }

    @Around("pc2()")
    public void around2(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("=======代理方法：around before=======");
        joinPoint.proceed();
        System.out.println("=======代理方法：around after=======");
    }

}
