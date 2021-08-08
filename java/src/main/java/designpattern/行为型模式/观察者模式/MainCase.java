package designpattern.行为型模式.观察者模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:01
 **/
public class MainCase {
    public static void main(String[] args) {
        Subject subject = new SubjectImpl();

        Observer observerA = new ObserverImplA();
        Observer observerB = new ObserverImplB();

        subject.add(observerA);
        subject.add(observerB);

        subject.notifyObserver();
    }
}
