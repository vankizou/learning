package com.zoufanqi.designpattern.行为型模式._17_状态模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 13:17
 **/
public class MainCase {
    public static void main(String[] args) {
        // 创建环境
        Context context = new Context();

        // 处理请求
        context.handle();
        context.handle();
        context.handle();
        context.handle();
    }
}
