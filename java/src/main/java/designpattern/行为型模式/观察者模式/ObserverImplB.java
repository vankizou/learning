package designpattern.行为型模式.观察者模式;


/**
 * 具体观察者B
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 19:47
 **/
public class ObserverImplB implements Observer{
    @Override
    public void response() {
        System.out.println("具体观察者B作出反应……");
    }
}
