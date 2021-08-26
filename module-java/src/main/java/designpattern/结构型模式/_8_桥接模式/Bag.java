package designpattern.结构型模式._8_桥接模式;

/**
 * 抽象化角色：包
 *
 * @author: ZOUFANQI
 * @create: 2021-08-07 09:31
 **/
public abstract class Bag {
    protected Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract String getName();
}
