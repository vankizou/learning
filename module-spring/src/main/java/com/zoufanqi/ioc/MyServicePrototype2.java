package com.zoufanqi.ioc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 注解生成bean
 *
 * @author: ZOUFANQI
 * @create: 2021-09-02 18:02
 **/
@Service
@Scope("prototype")
public class MyServicePrototype2 {
    public int b = 0;
}
