package designpattern.结构型模式._10_代理模式._2_动态代理java;

/**
 * java内置动态代理实例必须实现接口
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 15:15
 **/
public class MainCase {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();

        // 代理实例（必须为接口），这是因为动态生成的代理类已经继承了Proxy类，而java为单继承，就不能再继承其他的类了
        Subject proxyIns = ProxyHandler.getProxyObj(realSubject);

        // 如果是类，抛出异常：java.lang.ClassCastException
//        RealSubject proxyIns = ProxyHandler.getProxyObj(realSubject);

        // 执行代理业务，所有方法都会过代理
        System.out.println("result: " + proxyIns.request());
        System.out.println("result: " + proxyIns.request2());
    }
}
