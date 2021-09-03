package com.zoufanqi.designpattern.结构型模式._11_享元模式;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 11:25
 **/
public class FlyweightFactory {
    private static final Map<String, Flyweight> FLYWEIGHTS = new HashMap<>();

    public static Flyweight getFlyweight(String key) {
        Flyweight flyweight = FLYWEIGHTS.get(key);
        if (flyweight == null) {
            flyweight = new FlyweightImpl(key);
            FLYWEIGHTS.put(key, flyweight);
        } else {
            System.out.println("具体享元" + key + "已存在，被成功获取！");
        }
        return flyweight;
    }
}
