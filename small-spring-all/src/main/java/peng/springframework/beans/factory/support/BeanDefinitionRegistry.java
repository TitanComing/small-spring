package peng.springframework.beans.factory.support;

import peng.springframework.beans.factory.config.BeanDefinition;

/**
 * Create by peng on 2021/9/10.
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
