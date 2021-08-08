package designpattern.结构型模式._12_组合模式;

/**
 * 抽象构件
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 15:01
 **/
public interface Component {
    void add(Component c);

    void remove(Component c);

    Component getChild(int i);

    void operation();
}
