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
    // 此时对象还未生成，所以可以返回一个新对象，替换原来要生成的对象
    // 如果返回了替换对象，则后续只执行postProcessAfterInstantiation方法，其他方法不执行
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    // bean对象执行初始化方法之后处理
    // 对象已经实例化，但是没有进行变量赋值。如果返回false,则忽略属性值的设置操作
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    // bean独享实例化之后，设置属性之前执行的属性处理方法
    // 这个方法可以更改将要设置的属性
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;

}
