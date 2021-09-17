package peng.springframework.beans.factory.support;

import peng.springframework.beans.BeansException;
import peng.springframework.core.io.DefaultResourceLoader;
import peng.springframework.core.io.Resource;
import peng.springframework.core.io.ResourceLoader;

/**
 * Create by peng on 2021/09/17.
 * bean注册的抽象实现方法
 * 这里只实现了注册和获取资源加载类，具体加载由子类实现
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    //只有子类才能使用默认的资源加载器，其他的外部调用必须提供自己的资源加载器
    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry){
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader){
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegitry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}
