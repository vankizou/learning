package designpattern.结构型模式._6_适配器模式.对象适配器;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-06 21:07
 **/
public class MainCase {
    public static void main(String[] args) {
        Adapter adapter = new Adapter();
        Target targetAdapter = new ObjectAdapter(adapter);
        targetAdapter.show();
    }
}
