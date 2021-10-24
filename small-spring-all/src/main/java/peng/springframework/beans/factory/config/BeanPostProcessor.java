package peng.springframework.beans.factory.config;

import peng.springframework.beans.BeansException;

/**
 * Create by peng on 2021/09/18.
 * bean实例化前后修改
 */
public interface BeanPostProcessor {

    /**
     * bean对象已经实例化了，但是还没有赋值初始化，执行此方法
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * bean对象初始化复制完成，执行此方法
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
