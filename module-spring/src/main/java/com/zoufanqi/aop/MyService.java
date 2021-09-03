package com.zoufanqi.aop;

import org.springframework.stereotype.Service;

/**
 * @author: ZOUFANQI
 * @create: 2021-09-02 18:02
 **/
@Service
public class MyService {

    public void doSomething() {
        System.out.println("我就是个干活的...");
    }

}
