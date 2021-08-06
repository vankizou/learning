package designpattern.结构型模式.适配器模式.类适配器;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-06 20:57
 **/
public class MainCase {
    public static void main(String[] args) {
        Target targetAdapter = new ClassAdapter();
        targetAdapter.show();
    }
}
