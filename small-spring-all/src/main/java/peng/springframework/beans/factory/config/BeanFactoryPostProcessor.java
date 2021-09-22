package peng.springframework.beans.factory.config;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * Create by peng on 2021/09/18.
 * 允许自定义修改 BeanDefinition 属性信息
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
