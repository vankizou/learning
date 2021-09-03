package com.zoufanqi.designpattern.行为型模式._22_备忘录模式;

/**
 * 发起人
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:24
 **/
public class Originator {
    private String state;

    public Memento createMemento() {
        return new Memento(state);
    }

    public void restoreMemento(Memento m) {
        this.setState(m.getState());
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
