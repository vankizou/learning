package com.zoufanqi.designpattern.行为型模式._22_备忘录模式;

/**
 * 管理者
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:25
 **/
public class Caretaker {
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
