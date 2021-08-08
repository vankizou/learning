package designpattern.行为型模式._18_观察者模式;


/**
 * 具体观察者A
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 19:47
 **/
public class ObserverImplA implements Observer{
    @Override
    public void response() {
        System.out.println("具体观察者A作出反应……");
    }
}
