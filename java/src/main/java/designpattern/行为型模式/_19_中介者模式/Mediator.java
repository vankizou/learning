package designpattern.行为型模式._19_中介者模式;

/**
 * 抽象中介者
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:18
 **/
public abstract class Mediator {
    public abstract void register(Colleague colleague);

    /**
     * 转发
     *
     * @param colleague
     */
    public abstract void relay(Colleague colleague);
}
