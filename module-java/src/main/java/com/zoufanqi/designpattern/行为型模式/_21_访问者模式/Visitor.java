package com.zoufanqi.designpattern.行为型模式._21_访问者模式;

/**
 * 抽象访问者
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:11
 **/
public interface Visitor {
    void visit(ElementImplA element);

    void visit(ElementImplB element);
}
