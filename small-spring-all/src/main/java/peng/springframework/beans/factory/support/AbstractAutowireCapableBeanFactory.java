package peng.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.ProperlyValues;
import peng.springframework.beans.factory.PropertyValue;
import peng.springframework.beans.factory.config.BeanDefinition;
import peng.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * Create by peng on 2021/9/10.
 * 各个类实现自己的这一层职责的方法
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    //bean工厂直接提供了默认的构造策略，字类可以set或者get这个构建测率
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            //创建bean实例
            bean = createBeanInstance(beanDefinition, beanName, args);
            //填充bean域属性
            applyProperyValues(beanName, bean, beanDefinition);

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
            ProperlyValues properlyValues = beanDefinition.getProperlyValues();
            for (PropertyValue properlyValue : properlyValues.getPropertyValues()) {

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

}
