package peng.springframework.beans.factory.support;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.BeanFactory;
import peng.springframework.beans.factory.config.BeanDefinition;

/**
 * Create by peng on 2021/9/10.
 * 这里主要是模板模式的应用：1-可以将抽象方法留给字类实现  2-将方法置为protect,允许字类覆写
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if(bean != null){
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

}
