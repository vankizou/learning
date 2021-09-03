package com.zoufanqi.designpattern.行为型模式._16_责任链模式;

/**
 * 具体处理者角色A
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 12:55
 **/
public class HandlerImplA extends Handler {
    @Override
    public void handleRequest(String request) {
        if ("one".equals(request)) {
            System.out.println("具体处理者A负责处理该请求！");
        } else {
            if (this.getNext() != null) {
                this.getNext().handleRequest(request);
            } else {
                System.out.println("没有人处理该请求！");
            }
        }
    }
}
