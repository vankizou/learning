package designpattern.行为型模式._21_访问者模式;

/**
 * 抽象元素类
 *
 * @author: ZOUFANQI
 * @create: 2021-08-08 21:12
 **/
public interface Element {
    void accept(Visitor visitor);
}
