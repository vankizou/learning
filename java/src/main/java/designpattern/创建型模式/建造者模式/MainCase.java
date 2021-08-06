package designpattern.创建型模式.建造者模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-05 19:06
 **/
public class MainCase {
    public static void main(String[] args) {
        Builder builder = new Builder1();
        Director director = new Director(builder);
        Product product = director.construct();
        product.show();
    }
}
