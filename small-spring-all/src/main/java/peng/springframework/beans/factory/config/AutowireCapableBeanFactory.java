package peng.springframework.beans.factory.config;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.BeanFactory;

/**
 * Create by peng on 2021/9/14.
 * 预留自动处理bean工厂配置
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
