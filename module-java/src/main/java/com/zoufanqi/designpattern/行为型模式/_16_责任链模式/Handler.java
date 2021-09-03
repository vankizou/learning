package com.zoufanqi.designpattern.行为型模式._16_责任链模式;

/**
 * 抽象处理者角色
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 12:54
 **/
public abstract class Handler {
    private Handler next;

    public Handler getNext() {
        return next;
    }

    public void setNext(Handler next) {
        this.next = next;
    }

    /**
     * 处理请求的方法
     *
     * @param request
     */
    public abstract void handleRequest(String request);
}
