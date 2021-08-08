package designpattern.行为型模式.迭代器模式;

/**
 * 抽象迭代器
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 20:32
 **/
public interface Iterator {
    Object first();

    Object next();

    boolean hasNext();
}
