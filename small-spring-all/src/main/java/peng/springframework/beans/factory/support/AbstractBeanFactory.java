package peng.springframework.beans.factory.support;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.BeanFactory;
import peng.springframework.beans.factory.FactoryBean;
import peng.springframework.beans.factory.config.BeanDefinition;
import peng.springframework.beans.factory.config.BeanPostProcessor;
import peng.springframework.beans.factory.config.ConfigurableBeanFactory;
import peng.springframework.util.ClassUtils;
import peng.springframework.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by peng on 2021/9/10.
 * 这里主要是模板模式的应用：1-可以将抽象方法留给字类实现  2-将方法置为protect,允许字类覆写
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    //这个地方提供ClassLoader,给子类调用
    //目前是默认的ClassLoader
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    //抽象的bean工厂这里维持BeanPostProcessor的列表
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    //解析注解属性值
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {
        //普通bean实例和工厂bean实例都被缓存了，所以要统一判断是不是要进一步生成，判断的逻辑进行了统一的封装
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        // 普通bean实例和工厂bean实例都要交给spring的，如果是工厂bean实例，spring需要进一步调用工厂bean实例方法生成bean实例对象
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        // 先查工厂bean对应的缓存
        Object object = getCachedObjectForFactoryBean(beanName);
        // 缓冲中没有则创建
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        //这里先移除再添加，保证不重复
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        // 这个相当于用每个解析器都解析了一遍，不同解析器对应不同的占位符，命中则处理，不命中则不处理
        for(StringValueResolver resolver: this.embeddedValueResolvers){
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
}
