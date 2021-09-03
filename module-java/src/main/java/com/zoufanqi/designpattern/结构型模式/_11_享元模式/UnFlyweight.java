package com.zoufanqi.designpattern.结构型模式._11_享元模式;

/**
 * 非享元角色
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 11:21
 **/
public class UnFlyweight {
    private String info;

    public UnFlyweight(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
