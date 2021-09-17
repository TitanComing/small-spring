package peng.springframework.beans.factory;

import peng.springframework.beans.BeansException;

import java.util.Map;

/**
 * Create by peng on 2021/9/15.
 * bean工厂的扩展接口
 */
public interface ListableBeanFactory extends BeanFactory{

    //按照类型返回bean实例
    <T> Map<String, T> getBeansOfType(Class<T> Type) throws BeansException;

    //返回注册表中所有的Bean名称
    String[] getbeanDefinitionNames();

}
