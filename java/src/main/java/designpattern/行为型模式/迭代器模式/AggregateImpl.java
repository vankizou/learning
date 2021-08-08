package designpattern.行为型模式.迭代器模式;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体聚合
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:33
 **/
public class AggregateImpl implements Aggregate {
    private List<Object> list = new ArrayList<>();

    @Override
    public void add(Object obj) {
        list.add(obj);
    }

    @Override
    public void remove(Object obj) {
        list.remove(obj);
    }

    @Override
    public Iterator getIterator() {
        return new IteratorImpl(list);
    }
}
