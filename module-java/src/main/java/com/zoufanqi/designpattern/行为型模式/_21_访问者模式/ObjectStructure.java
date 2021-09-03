package com.zoufanqi.designpattern.行为型模式._21_访问者模式;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 对象结构角色
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:16
 **/
public class ObjectStructure {
    private List<Element> list = new ArrayList<>();

    public void accept(Visitor visitor) {
        Iterator<Element> it = list.iterator();
        while (it.hasNext()) {
            it.next().accept(visitor);
        }
    }

    public void add(Element element) {
        list.add(element);
    }

    public void remove(Element element) {
        list.remove(element);
    }
}
