package peng.springframework.beans.factory.config;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.PropertyValues;

/**
 * Create by peng on 2021/10/14.
 *
 * 定义一个BeanPostProcessor类型，用于将代理对象整合进入aop的生命周期中
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    // bean对象执行初始化方法之前，执行此方法
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    // bean独享实例化之后，设置属性之前执行的属性处理方法
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;

}
