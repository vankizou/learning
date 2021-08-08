package designpattern.行为型模式._19_中介者模式;

/**
 * 具体同事类A
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:19
 **/
public class ColleagueImplA extends Colleague {
    @Override
    public void receive() {
        System.out.println("具体同事类A收到请求……");
    }

    @Override
    public void send() {
        System.out.println("具体同事类A发出请求……");

        // 请中介者转发
        mediator.relay(this);
    }
}
