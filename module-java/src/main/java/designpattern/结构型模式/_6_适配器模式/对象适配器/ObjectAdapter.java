package designpattern.结构型模式._6_适配器模式.对象适配器;

/**
 * 对象适配器
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 20:59
 **/
public class ObjectAdapter implements Target {
    private Adapter adapter;

    public ObjectAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void show() {
        this.adapter.doAdapter();
        System.out.println("对象适配器：适配成功！");
    }
}
