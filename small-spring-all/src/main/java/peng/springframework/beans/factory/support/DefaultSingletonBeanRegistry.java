package peng.springframework.beans.factory.support;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.DisposableBean;
import peng.springframework.beans.factory.ObjectFactory;
import peng.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by peng on 2021/9/9.
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    //定义一个空对象
    protected static final Object NULL_OBJECT = new Object();

    //一级缓存，缓存普通对象：bean name --> bean instance
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    //二级缓存，提前暴漏对象，缓存没有完全实例化的对象：bean name --> bean instance
    //是个protected对象，子类可以直接获取
    protected final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    //三级缓存，存放工厂对象（代理对象也是按照工厂对象设置出来的）：bean name --> ObjectFactory
    private final Map<String, ObjectFactory<?>> singletonFactories = new ConcurrentHashMap<>();

    //带有销毁方法的bean也是要单独存储的，这个地方其实放入的是适配器
    private final Map<String, DisposableBean> disposableBeans = new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        //先尝试从一级缓存中获取
        Object singletonObject = singletonObjects.get(beanName);
        if(null == singletonObject){
            singletonObject = earlySingletonObjects.get(beanName);
            // 判断二级缓存中是否有对象，如果二级缓存中也没有，就查三级缓存。
            // 三级缓存中一定是代理对象
            if(null == singletonObject){
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if(singletonFactory != null){
                    singletonObject = singletonFactory.getObject();
                    //三级缓存中的对象要处理成真正的对象，放到二级缓存中
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory){
        if(!this.singletonObjects.containsKey(beanName)){
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    //这个地方是在另外一个地方实现，然后通过继承传递给了需要的地方
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    //注意这个接口实现，是一种服务分块封装的思想
    // AbstractBeanFactory实现了ConfigurableBeanFactory的这个接口，
    // 但是实现的方法是通过继承 DefaultSingletonBeanRegistry实现的。
    public void destroySingletons() {
        //这里的销毁是最后关闭的时候统一销毁的，所以统一维持
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

}
