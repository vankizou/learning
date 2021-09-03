package com.zoufanqi.designpattern.结构型模式._7_外观模式;

/**
 * 外观角色
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 11:13
 **/
public class Facade {
    private SubSystem1 s1 = new SubSystem1();
    private SubSystem2 s2 = new SubSystem2();
    private SubSystem3 s3 = new SubSystem3();

    public void action() {
        s1.action1();
        s2.action2();
        s3.action3();
    }
}
