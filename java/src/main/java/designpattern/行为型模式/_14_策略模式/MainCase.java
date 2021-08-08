package designpattern.行为型模式._14_策略模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 12:04
 **/
public class MainCase {
    public static void main(String[] args) {
        Context context = new Context();
        Strategy strategyA = new StrategyImplA();
        context.setStrategy(strategyA);
        context.strategyMethod();

        System.out.println("---------------");

        Strategy strategyB = new StrategyImplB();
        context.setStrategy(strategyB);
        context.strategyMethod();
    }
}
