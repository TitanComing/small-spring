package peng.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import peng.springframework.aop.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create by peng on 2021/9/29.
 * <p>
 * jdk动态代理
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //用户自己提供了方法拦截实现，则作反射调用
        if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            // ReflectivemethodInvocation是一个入参包装类，提供：目标对象、方法、入参。
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args));
        }

        return method.invoke(advised.getTargetSource().getTarget(), args);
    }

    //这个代理类把自己作为InvocationHandler对象传入了，将会调用自己的invoke方法
    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), advised.getTargetSource().getTargetClass(), this);
    }
}
