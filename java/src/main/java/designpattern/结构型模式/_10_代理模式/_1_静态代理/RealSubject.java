package designpattern.结构型模式._10_代理模式._1_静态代理;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-06 15:11
 **/
public class RealSubject implements Subject {

    @Override
    public boolean request() {
        System.out.println("静态代理：访问真实主题……");
        return true;
    }
}
