package com.zoufanqi.designpattern.行为型模式._23_解释器模式;

/**
 * 抽象表达式类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:31
 **/
public interface Expression {
    /**
     * 解释方法
     */
    boolean interpret(String info);
}
