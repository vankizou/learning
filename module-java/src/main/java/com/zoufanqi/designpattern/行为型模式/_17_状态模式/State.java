package com.zoufanqi.designpattern.行为型模式._17_状态模式;

/**
 * 抽象状态类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 13:12
 **/
public abstract class State {
    public abstract void handle(Context context);
}
