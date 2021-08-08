package designpattern.行为型模式.状态模式;

/**
 * 抽象状态类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 13:12
 **/
public abstract class State {
    public abstract void handle(Context context);
}
