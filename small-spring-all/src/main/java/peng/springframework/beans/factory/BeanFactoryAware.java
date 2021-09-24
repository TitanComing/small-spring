package peng.springframework.beans.factory;

import peng.springframework.beans.BeansException;

/**
 * Create by peng on 2021/09/24.
 *
 * 传递bean工厂实例
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
