package peng.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import peng.springframework.beans.BeansException;
import peng.springframework.beans.PropertyValues;
import peng.springframework.beans.PropertyValue;
import peng.springframework.beans.factory.config.AutowireCapableBeanFactory;
import peng.springframework.beans.factory.config.BeanDefinition;
import peng.springframework.beans.factory.config.BeanPostProcessor;
import peng.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

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
            //创建bean实例
            bean = createBeanInstance(beanDefinition, beanName, args);
            //填充bean域属性
            applyProperyValues(beanName, bean, beanDefinition);
            //执行前置、初始化、后置方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        addSingleton(beanName, bean);
        return bean;
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

        //1. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        //2. 这里的invokeInitMethods才是真正执行初始话bean实例的方法
        invokeInitMethods(beanName, wrappedBean, beanDefinition);

        //3. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);

        return wrappedBean;
    }

    //todo 初始化bean实例，这里之前有个创建bean实例了，有啥区别？
    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {

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
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            //如果处理之后无法返回对象，则返回原来没有处理的入参对象
            if (null == current) return result;
            result = current;
        }
        return result;
    }
}
