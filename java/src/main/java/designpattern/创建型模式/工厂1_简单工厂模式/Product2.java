package designpattern.创建型模式.工厂1_简单工厂模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-04 19:02
 **/
public class Product2 implements Product {
    @Override
    public void show() {
        System.out.println("简单工厂：我是型号2产品，我会跳！");
    }
}
