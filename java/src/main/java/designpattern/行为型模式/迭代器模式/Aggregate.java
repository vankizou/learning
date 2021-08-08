package designpattern.行为型模式.迭代器模式;

/**
 * 抽象聚合
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:31
 **/
public interface Aggregate {
    void add(Object obj);

    void remove(Object obj);

    Iterator getIterator();
}
