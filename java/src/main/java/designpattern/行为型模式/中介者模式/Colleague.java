package designpattern.行为型模式.中介者模式;

/**
 * 抽象同事类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:17
 **/
public abstract class Colleague {
    protected Mediator mediator;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void receive();

    public abstract void send();
}
