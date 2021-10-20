package peng.springframework.beans.factory.annotation;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.PropertyValues;
import peng.springframework.beans.factory.BeanFactory;
import peng.springframework.beans.factory.BeanFactoryAware;
import peng.springframework.beans.factory.ConfigurableListableBeanFactory;
import peng.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * Create by peng on 2021/10/20.
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // 1. 处理注解 @value

        // 2. 处理注解 @Autowired


        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

}
