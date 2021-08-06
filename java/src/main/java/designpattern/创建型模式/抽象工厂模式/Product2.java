package designpattern.创建型模式.抽象工厂模式;

/**
 * 产品2
 *
 * @author: ZOUFANQI
 * @create: 2021-08-05 18:04
 **/
public class Product2 implements Product {

    @Override
    public void show() {
        System.out.println("抽象工厂：我是产品2，我能飞上太空~");
    }
}
