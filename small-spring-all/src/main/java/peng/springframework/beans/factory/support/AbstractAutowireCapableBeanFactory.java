package peng.springframework.beans.factory.support;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.config.BeanDefinition;

/**
 * Create by peng on 2021/9/10.
 * 各个类实现自己的这一层职责的方法
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        try {
            //todo 无法构造带参数实例
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        addSingleton(beanName, bean);
        return bean;
    }

}
