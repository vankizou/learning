package designpattern.行为型模式._18_观察者模式;

/**
 * 具体目标
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 19:58
 **/
public class SubjectImpl extends Subject {
    @Override
    public void notifyObserver() {
        System.out.println("具体目标发生改变……");
        System.out.println("--------------");

        for (Observer o : observers) {
            o.response();
        }
    }
}
