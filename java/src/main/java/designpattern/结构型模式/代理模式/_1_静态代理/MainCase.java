package designpattern.结构型模式.代理模式._1_静态代理;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-06 15:15
 **/
public class MainCase {
    public static void main(String[] args) {
        boolean result = new ProxyHandler().request();
        System.out.println("result: " + result);
    }
}
