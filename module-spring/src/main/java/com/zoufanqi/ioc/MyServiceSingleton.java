package com.zoufanqi.ioc;

import org.springframework.stereotype.Service;

/**
 * 注解生成bean
 *
 * @author: ZOUFANQI
 * @create: 2021-09-02 18:02
 **/
@Service
public class MyServiceSingleton {
    public final MyServicePrototype2 myServicePrototype2;
    public final MyServiceSingleton2 myServiceSingleton2;
    public int a = 0;

    public MyServiceSingleton(MyServicePrototype2 myServicePrototype2, MyServiceSingleton2 myServiceSingleton2) {
        this.myServicePrototype2 = myServicePrototype2;
        this.myServiceSingleton2 = myServiceSingleton2;
    }

}
