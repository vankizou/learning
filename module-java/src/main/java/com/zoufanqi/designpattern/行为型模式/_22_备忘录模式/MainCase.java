package com.zoufanqi.designpattern.行为型模式._22_备忘录模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:25
 **/
public class MainCase {
    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();
        originator.setState("S0");
        System.out.println("初始状态：" + originator.getState());

        // 保存状态
        caretaker.setMemento(originator.createMemento());

        originator.setState("S1");
        System.out.println("新的状态：" + originator.getState());

        // 恢复状态
        originator.restoreMemento(caretaker.getMemento());
        System.out.println("恢复状态：" + originator.getState());
    }
}
