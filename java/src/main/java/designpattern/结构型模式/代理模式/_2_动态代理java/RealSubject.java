package designpattern.结构型模式.代理模式._2_动态代理java;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-06 15:11
 **/
public class RealSubject implements Subject {

    @Override
    public boolean request() {
        System.out.println("动态代理java: 访问真实主题……");
        return true;
    }

    @Override
    public boolean request2() {
        System.out.println("动态代理java: 访问真实主题2……");
        return true;
    }
}
