package designpattern.结构型模式.代理模式._3_动态代理cglib;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-06 15:11
 **/
public class RealSubject implements Subject {
    public boolean request() {
        System.out.println("动态代理cglib: 访问真实主题……");
        return true;
    }
}
