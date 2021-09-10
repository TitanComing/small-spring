package peng.springframework.beans.factory.support;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * Create by peng on 2021/9/10.
 * 实例化策略
 * bean工厂实例化对象也是专业操作，值得封装出来
 */
public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;

}
