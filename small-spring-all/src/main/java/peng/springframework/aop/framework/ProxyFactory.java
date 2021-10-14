package peng.springframework.aop.framework;

import peng.springframework.aop.AdvisedSupport;

/**
 * Create by peng on 2021/10/14.
 * <p>
 * 创建代理工厂，用于生成代理对象
 * 主要用于区分cglib和jdk两种动态代理的创建
 */
public class ProxyFactory {

    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (advisedSupport.isProxyTargetClass()) {
            return new Cglib2AopProxy(advisedSupport);
        }

        return new JdkDynamicAopProxy(advisedSupport);
    }

}
