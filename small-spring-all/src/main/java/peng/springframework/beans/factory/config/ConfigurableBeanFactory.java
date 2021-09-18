package peng.springframework.beans.factory.config;

import peng.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * Create by peng on 2021/9/15.
 * 可以配置化的bean工厂，也就是可以获取beanPostProcessor,beanClassLoader等信息配置化接口
 * 这里可以定义一些实例化bean的策略
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETION = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    //对bean实例化的处理，就放到可以配置话的bean工厂
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
