package peng.springframework.beans.factory.config;

import peng.springframework.beans.BeansException;

/**
 * Create by peng on 2021/10/14.
 *
 * 定义一个BeanPostProcessor类型，用于将代理对象整合进入aop的生命周期中
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    // bean对象执行初始化方法之前，执行此方法
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

}
