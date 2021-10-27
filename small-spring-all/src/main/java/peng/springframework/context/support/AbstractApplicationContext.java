package peng.springframework.context.support;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.ConfigurableListableBeanFactory;
import peng.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import peng.springframework.beans.factory.config.BeanFactoryPostProcessor;
import peng.springframework.beans.factory.config.BeanPostProcessor;
import peng.springframework.context.ApplicationEvent;
import peng.springframework.context.ApplicationListener;
import peng.springframework.context.ConfigurableApplicationContext;
import peng.springframework.context.event.ApplicationEventMulticaster;
import peng.springframework.context.event.ContextClosedEvent;
import peng.springframework.context.event.ContextRefreshedEvent;
import peng.springframework.context.event.SimpleApplicationEventMulticaster;
import peng.springframework.core.convert.ConversionService;
import peng.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * Create by peng on 2021/09/18.
 * 提供抽象的应用上下文实现
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    //事件广播站名称
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    //事件广播站实例
    private ApplicationEventMulticaster applicationEventMulticaster;

    //刷新方法需要放出给用户的接口，对用户提供的的接口用模板方法封装
    @Override
    public void refresh() throws BeansException {
        // 1. 创建 BeanFactory, 并加载 BeanDefinition
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        //    也就是继承ApplicationContextAware 的 Bean设置信息，是在ApplicationContextAwareProcessor中进行的
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4. 执行BeanFactoryPostProcesser 中的方法，处理加载好的 BeanDefinition
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5. 要先注册BeanPostProcessor, 之后实例化的时候才能用
        registerBeanPostProcessors(beanFactory);

        // 6. 初始化事件广播站
        initApplicationEventMulticaster();

        // 7. 注册事件监听者
        registerListeners();

        // 8. 设置类型转换，实例化单例Bean对象
        finishBeanFactoryInitialization(beanFactory);

        // 9. 初始化事件广播站、监听者，就可以发布第一个事件了-容器刷新完成事件
        finishRefresh();

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

    private void initApplicationEventMulticaster(){
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners(){
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for(ApplicationListener listener: applicationListeners){
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    // 引入类型转换器，提前实例化单例bean对象
    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory){
        // 设置类型转换器-这个地方是把bean名称写死的
        if(beanFactory.containsBean("conversionService")){
            Object conversionService = beanFactory.getBean("conversionService");
            // 因为前边是bean名称，所以这个地方要校验下类是否相同
            if(conversionService instanceof ConversionService){
                beanFactory.setConversionService((ConversionService) conversionService);
            }
        }

        // 实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    //ApplicationContext实现了事件发布者的接口，具有事件发布能力
    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
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
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
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
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行销毁单例bean的销毁方法
        getBeanFactory().destroySingletons();
    }
}
