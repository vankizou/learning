package designpattern.结构型模式._10_代理模式._2_动态代理java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理干活的，可以扩展做的事
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 15:11
 **/
public class ProxyHandler<T> implements InvocationHandler {
    private T realObj;

    public ProxyHandler(T realObj) {
        this.realObj = realObj;
    }

    public static <T> T getProxyObj(T realObj) {
        return (T) Proxy.newProxyInstance(
                realObj.getClass().getClassLoader(),
                realObj.getClass().getInterfaces(),
                new ProxyHandler(realObj)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        preRequest();
        Object result = method.invoke(realObj, args);
        afterRequest();
        return result;
    }

    private void preRequest() {
        System.out.println("动态代理java: 访问真实主题之前的准备工作……");
    }

    private void afterRequest() {
        System.out.println("动态代理java: 访问真实主题之后的后续工作……");
    }
}
