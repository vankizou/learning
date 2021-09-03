package com.zoufanqi.designpattern.结构型模式._9_装饰器模式;

/**
 * 抽象装饰角色
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 10:52
 **/
public class Decorator implements Component {
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation();
        System.out.println("抽象装饰角色添加了一盏灯……");
    }
}
