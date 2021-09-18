package peng.springframework.beans.factory;


import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.config.AutowireCapableBeanFactory;
import peng.springframework.beans.factory.config.BeanDefinition;
import peng.springframework.beans.factory.config.BeanPostProcessor;
import peng.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Create by peng on 2021/9/15.
 * 提供分析和修改 Bean 以及预先实例化的操作接口
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
