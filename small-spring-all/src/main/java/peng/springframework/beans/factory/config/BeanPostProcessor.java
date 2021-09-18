package peng.springframework.beans.factory.config;

import peng.springframework.beans.BeansException;

/**
 * Create by peng on 2021/09/18.
 * bean实例化前后修改
 */
public interface BeanPostProcessor {

    /**
     * bean对象执行初始化方法之前，执行此方法
     */
    // todo 实例化之前没有对象，对象怎么作为入参？
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * bean对象执行初始化方法之后，执行此方法
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
