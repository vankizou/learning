package com.zoufanqi.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用配置的方法生成bean
 *
 * @author: ZOUFANQI
 * @create: 2021-09-04 09:50
 **/
@Configuration
public class MyService2Configuration {

    @Bean
    public MyService2 myServiceBean() {
        return new MyService2();
    }
}
