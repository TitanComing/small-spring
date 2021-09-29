package peng.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * Create by peng on 2021/9/29.
 *
 * 将代理、拦截、匹配的各项属性包装到一个类中，方便在Proxy类实现的时候调用
 */
public class AdvisedSupport {

    // ProxyConfig信息
    private boolean proxyTargetClass = false;

    // 被代理的目标对象
    private TargetSource targetSource;
    // 方法拦截器，交给用户实现自己的拦截逻辑，最终会调用其中的invoke方法
    private MethodInterceptor methodInterceptor;
    // 方法匹配器-检查拦截的方法是否进行通知
    private MethodMatcher methodMatcher;

    public boolean isProxyTargetClass(){
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass){
        this.proxyTargetClass = proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
