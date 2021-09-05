package com.zoufanqi.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 注解生成bean
 *
 * @author: ZOUFANQI
 * @create: 2021-09-02 18:02
 **/
@Service
public class MyService {

    public void doSomething() {
        System.out.println("我就是个干活的...");
    }

}
