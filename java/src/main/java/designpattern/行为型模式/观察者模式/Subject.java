package designpattern.行为型模式.观察者模式;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象目标
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 13:28
 **/
public abstract class Subject {
    protected List<Observer> observers = new ArrayList<>();

    /**
     * 增加观察者
     *
     * @param observer
     */
    public void add(Observer observer) {
        observers.add(observer);
    }

    /**
     * 删除观察者
     *
     * @param observer
     */
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    /**
     * 通知观察者
     */
    public abstract void notifyObserver();
}
