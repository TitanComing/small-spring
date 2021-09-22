package peng.springframework.common;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.PropertyValue;
import peng.springframework.beans.PropertyValues;
import peng.springframework.beans.factory.ConfigurableListableBeanFactory;
import peng.springframework.beans.factory.config.BeanDefinition;
import peng.springframework.beans.factory.config.BeanFactoryPostProcessor;
import peng.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Create by peng on 2021/09/22.
 */
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //已经加载完成beanDefinition了，可以做一点事情了
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "改为：啦啦啦"));

    }
}
