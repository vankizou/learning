package designpattern.创建型模式.抽象工厂模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-05 18:38
 **/
public class MainCase {

    public static void main(String[] args) {
        // 真实业务场景中工厂一般为单例
        new Factory1().createProduct1();
        new Factory1().createProduct2();
    }

}
