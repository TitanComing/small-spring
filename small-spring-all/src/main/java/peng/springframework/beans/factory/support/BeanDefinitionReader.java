package peng.springframework.beans.factory.support;

import peng.springframework.beans.BeansException;
import peng.springframework.core.io.Resource;
import peng.springframework.core.io.ResourceLoader;

/**
 * Create by peng on 2021/9/10.
 * 承接从io读取的inputStream, 转换为 beandefintion
 */
public interface BeanDefinitionReader {

    //定义bean可以注册到的地方
    BeanDefinitionRegistry getRegistry();
    //定义获取bean方式
    ResourceLoader getResourceLoader();


    // 定义转换方法
    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;

}
