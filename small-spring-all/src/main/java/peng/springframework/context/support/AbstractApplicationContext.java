package peng.springframework.context.support;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.ConfigurableListableBeanFactory;
import peng.springframework.beans.factory.config.BeanFactoryPostProcessor;
import peng.springframework.beans.factory.config.BeanPostProcessor;
import peng.springframework.context.ConfigurableApplicationContext;
import peng.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * Create by peng on 2021/09/18.
 * 提供抽象的应用上下文实现
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    //刷新方法需要放出给用户的接口，对用户提供的的接口用模板方法封装
    @Override
    public void refresh() throws BeansException {
        // 1. 创建 BeanFactory, 并加载 BeanDefinition
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 执行BeanFactoryPostProcesser 中的方法，处理加载好的 BeanDefinition
        invokeBeanFactoryPostProcessors(beanFactory);

        // 4. 要先注册BeanPostProcessor, 之后实例化的时候才能用
        registerBeanPostProcessors(beanFactory);

        // 5. 对于单例模式下的对象，可以提前实例化-也就是预加载的过程中已经触发了实例的调用链
        beanFactory.preInstantiateSingletons();

    }

    //refreshBeanFactory()创建bean工厂，getBeanFactory()获取bean工程，变量交给子类创建，实现，传递
    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        //这里可以注册一系列BeanFactoryPostProcessor，依次调用
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }
}
