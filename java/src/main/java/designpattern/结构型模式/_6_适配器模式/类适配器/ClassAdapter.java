package designpattern.结构型模式._6_适配器模式.类适配器;

/**
 * 类适配器
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 19:11
 **/
public class ClassAdapter extends Adapter implements Target {
    @Override
    public void show() {
        this.doAdapter();
        System.out.println("类适配器模式：适配成功！");
    }
}
