package com.zoufanqi.designpattern.行为型模式._20_迭代器模式;

import java.util.List;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:35
 **/
public class IteratorImpl implements Iterator {
    private List<Object> list = null;
    private int index = -1;

    public IteratorImpl(List<Object> list) {
        this.list = list;
    }

    @Override
    public Object first() {
        this.index = 0;
        return list.get(this.index);
    }

    @Override
    public Object next() {
        Object obj = null;
        if (this.hasNext()) {
            obj = list.get(++this.index);
        }
        return obj;
    }

    @Override
    public boolean hasNext() {
        return (this.index < list.size() - 1);
    }
}
