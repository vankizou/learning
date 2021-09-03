package com.zoufanqi.designpattern.行为型模式._21_访问者模式;

/**
 * 具体访问者A
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:15
 **/
public class VisitorImplA implements Visitor {
    @Override
    public void visit(ElementImplA element) {
        System.out.println("具体访问者A访问 -> " + element.operationA());
    }

    @Override
    public void visit(ElementImplB element) {
        System.out.println("具体访问者A访问 -> " + element.operationB());
    }
}
