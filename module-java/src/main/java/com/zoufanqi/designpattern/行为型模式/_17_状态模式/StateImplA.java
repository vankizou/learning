package com.zoufanqi.designpattern.行为型模式._17_状态模式;

/**
 * 具体状态A类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 13:14
 **/
public class StateImplA extends State {

    @Override
    public void handle(Context context) {
        System.out.println("当前状态是A...");
        context.setState(new StateImplB());
    }
}
