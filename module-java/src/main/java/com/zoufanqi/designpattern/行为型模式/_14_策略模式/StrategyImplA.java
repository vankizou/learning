package com.zoufanqi.designpattern.行为型模式._14_策略模式;

/**
 * 具体策略类A
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 12:02
 **/
public class StrategyImplA implements Strategy {
    @Override
    public void strategyMethod() {
        System.out.println("具体策略类A策略方法被访问……");
    }
}
