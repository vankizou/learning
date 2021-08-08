package designpattern.行为型模式.策略模式;

/**
 * 环境类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 12:03
 **/
public class Context {
    private Strategy strategy;

    public void strategyMethod() {
        strategy.strategyMethod();
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
