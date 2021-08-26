package designpattern.结构型模式._10_代理模式._1_静态代理;

/**
 * 代理干活的，可以扩展做的事
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 15:11
 **/
public class ProxyHandler implements Subject {
    private RealSubject realSubject;

    @Override
    public boolean request() {
        if (realSubject == null) {
            realSubject = new RealSubject();
        }
        preRequest();
        boolean result = realSubject.request();
        afterRequest();
        return result;
    }

    private void preRequest() {
        System.out.println("静态代理：访问真实主题之前的准备工作……");
    }

    private void afterRequest() {
        System.out.println("静态代理：访问真实主题之后的后续工作……");
    }
}
