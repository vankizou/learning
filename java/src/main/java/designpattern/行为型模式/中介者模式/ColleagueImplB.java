package designpattern.行为型模式.中介者模式;

/**
 * 具体同事类B
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:19
 **/
public class ColleagueImplB extends Colleague {
    @Override
    public void receive() {
        System.out.println("具体同事类B收到请求……");
    }

    @Override
    public void send() {
        System.out.println("具体同事类B发出请求……");

        // 请中介者转发
        mediator.relay(this);
    }
}
