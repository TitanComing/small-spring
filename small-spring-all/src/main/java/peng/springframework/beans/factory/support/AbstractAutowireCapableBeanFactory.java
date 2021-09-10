package peng.springframework.beans.factory.support;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * Create by peng on 2021/9/10.
 * 各个类实现自己的这一层职责的方法
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    //bean工厂直接提供了默认的构造策略，字类可以set或者get这个构建测率
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        addSingleton(beanName, bean);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        //通过入参何构造函数匹配查找对应的构造函数
        if(null != args){
            for (Constructor ctor : declaredConstructors) {
                if (ctor.getParameterTypes().length == args.length) {
                    for (int i = 0; i < args.length; i++) {
                        if (!ctor.getParameterTypes()[i].equals(args.getClass())) {
                            break;
                        }
                    }
                    constructorToUse = ctor;
                    break;
                }
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

}
