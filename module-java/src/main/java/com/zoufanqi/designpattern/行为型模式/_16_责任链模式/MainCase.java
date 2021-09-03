package com.zoufanqi.designpattern.行为型模式._16_责任链模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 12:56
 **/
public class MainCase {
    public static void main(String[] args) {
        // 组装责任链
        Handler handlerA = new HandlerImplA();
        Handler handlerB = new HandlerImplB();
        handlerA.setNext(handlerB);

        // 提交请求
        handlerA.handleRequest("one");
        System.out.println("------------");
        handlerA.handleRequest("two");
    }
}
