package designpattern.结构型模式._10_代理模式._3_动态代理cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 代理干活的，可以扩展做的事
 *
 * @author: ZOUFANQI
 * @create: 2021-08-06 15:11
 **/
public class ProxyHandler implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();

    public <T> T getProxyIns(Class<T> cls) {
        enhancer.setSuperclass(cls);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        preRequest();
        Object result = methodProxy.invokeSuper(o, args);
        afterRequest();
        return result;
    }

    private void preRequest() {
        System.out.println("动态代理cglib: 访问真实主题之前的准备工作……");
    }

    private void afterRequest() {
        System.out.println("动态代理cglib: 访问真实主题之后的后续工作……");
    }
}
