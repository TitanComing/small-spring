package peng.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import peng.springframework.beans.BeansException;
import peng.springframework.beans.PropertyValues;
import peng.springframework.beans.PropertyValue;
import peng.springframework.beans.factory.*;
import peng.springframework.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Create by peng on 2021/9/10.
 * 各个类实现自己的这一层职责的方法
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    //bean工厂直接提供了默认的构造策略，字类可以set或者get这个构建实例
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            //判断是否要返回Bean对象的代理对象-aop的流程
            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if (null != bean) {
                //这里直接返回了bean对象
                return bean;
            }
            //创建bean实例：这个地方在源码里边实际上返回的是个BeanWrapper,实例化是在下边执行的
            bean = createBeanInstance(beanDefinition, beanName, args);
            // 实例化之后，执行postProcessAfterInstantiation方法，顺便判定是不是进行后续的属性设置填充处理等处理
            boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
            if (!continueWithPropertyPopulation) {
                return bean;
            }
            //先通过BeanPostProcessor处理了注解，设置了一部分属性值，这里的pvs可以自己定义，目前直接用的beanDefiniton中的pvs
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
            //通过beanDefinition中的属性对，填充bean域属性，可能会对上面改的设置进行覆盖
            applyProperyValues(beanName, bean, beanDefinition);
            //执行前置、初始化、后置方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 注册实现了 DisposableBean 接口的 Bean 对象
        // 先把需要销毁的对象保存起来，方便后续销毁
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        if (beanDefinition.isSingleton()) {
            //单例才添加
            registerSingleton(beanName, bean);
        }

        return bean;
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean = null;

        //创建bean实例：这个地方在源码里边实际上返回的是个BeanWrapper,实例化是在下边执行的
        bean = createBeanInstance(beanDefinition, beanName, args);

        // 处理循环依赖-实际上就是已经实例化的对象提前放到缓存中暴漏出来
        if (beanDefinition.isSingleton()) {
            Object finalBean = bean;
            addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, beanDefinition, finalBean));
        }

        // 实例化之后，执行postProcessAfterInstantiation方法，顺便判定是不是进行后续的属性设置填充处理等处理
        boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
        if (!continueWithPropertyPopulation) {
            return bean;
        }
        //先通过BeanPostProcessor处理了注解，设置了一部分属性值，这里的pvs可以自己定义，目前直接用的beanDefiniton中的pvs
        applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
        //通过beanDefinition中的属性对，填充bean域属性，可能会对上面改的设置进行覆盖
        applyProperyValues(beanName, bean, beanDefinition);
        //执行前置、初始化、后置方法
        bean = initializeBean(beanName, bean, beanDefinition);
        // 注册实现了 DisposableBean 接口的 Bean 对象
        // 先把需要销毁的对象保存起来，方便后续销毁
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        if (beanDefinition.isSingleton()) {
            //单例才添加
            registerSingleton(beanName, bean);
        }

        return bean;

    }

    //处理二级缓存中半成品的引用
    protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean){
        Object exposedObject = bean;
        for(BeanPostProcessor beanPostProcessor:getBeanPostProcessors()){
            // 这个地方要增加beanPostProcessor接口了
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                exposedObject = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(exposedObject, beanName);
                if(null == exposedObject) return null;
            }
        }

        return exposedObject;
    }

    //Bean 实例化后对于返回 false 的对象，不在执行后续设置 Bean 对象属性的操作
    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        boolean continueWithPropertyPopulation = true;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor = (InstantiationAwareBeanPostProcessor) beanPostProcessor;
                //这个地方是判定，但是是直接调用了一次
                if (!instantiationAwareBeanPostProcessor.postProcessAfterInstantiation(bean, beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }

    // 正式填充属性前的处理
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                //这个地方其实就是原封不动返回的，所以没有发生变动，但是实际上可以创造新的pvs对象进行调用
                PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (null != pvs) {
                    for (PropertyValue propertyValue : pvs.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (null != bean) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) return result;
            }
        }
        return null;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {

        //非单例模式的bean不执行销毁方法: 非单例模式，内存没有统一维持，没有销毁的必要
        if (!beanDefinition.isSingleton()) return;

        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            //这里注册的是个适配器,通过适配器统一了销毁方法的调用：将通过接口方法的调用和通过销毁反射方法的调用都整合到了通过接口接口方法调用
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        //通过入参何构造函数匹配查找对应的构造函数
        if (null != args) {
            for (Constructor ctor : declaredConstructors) {
                if (ctor.getParameterTypes().length == args.length) {
                    for (int i = 0; i < args.length; i++) {
                        if (!ctor.getParameterTypes()[i].equals(args.getClass())) {
                            break;
                        }
                    }
                    constructorToUse = ctor;
                    break;
                }
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    /**
     * Bean 属性填充
     */
    protected void applyProperyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue properlyValue : propertyValues.getPropertyValues()) {

                String name = properlyValue.getName();
                Object value = properlyValue.getValue();

                //属性可能是个对象，所以要递归处理
                //todo 这里没有处理循环依赖的问题，之后处理
                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                //填充属性，这里方便用第三方封装的包，也可以自己实现
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values：" + beanName);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        //初始化bean的时候, 把需要放出去的信息都放出去
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        //1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        //2. 这里的invokeInitMethods才是真正执行初始话bean实例的方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        //3. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);

        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        //1. 这里可以判定下bean是否实现了 InitializingBean
        if (bean instanceof InitializingBean) {
            //继承了接口则调用接口的方法
            ((InitializingBean) bean).afterPropertiesSet();
        }

        //2. 注解配置 init-method {判断是为了避免二次执行销毁}
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethod) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }

    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        //调用BeanPostProcessor实例化之前的处理方法
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            //如果处理之后无法返回对象，则返回原来没有处理的入参对象
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        //调用BeanPostProcessor实例化之后的处理方法
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            //如果处理之后无法返回对象，则返回原来没有处理的入参对象
            if (null == current) return result;
            result = current;
        }
        return result;
    }
}
