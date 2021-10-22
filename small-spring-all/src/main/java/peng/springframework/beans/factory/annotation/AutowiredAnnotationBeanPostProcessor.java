package peng.springframework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import peng.springframework.beans.BeansException;
import peng.springframework.beans.PropertyValues;
import peng.springframework.beans.factory.BeanFactory;
import peng.springframework.beans.factory.BeanFactoryAware;
import peng.springframework.beans.factory.ConfigurableListableBeanFactory;
import peng.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import peng.springframework.util.ClassUtils;

import java.lang.reflect.Field;

/**
 * Create by peng on 2021/10/20.
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    // 这个方法把属性值放进去又原样返回了
    // 返回的pvs会调用一次设置，所有其实是一个预留接口，可以创造全新的pvs对象，调用进行一次设置
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // 1. 处理注解 @Value
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields) {
            //注意：这个地方用了注解类，注解本身也是个类型
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (null != valueAnnotation) {
                // 用注解对域变量赋值
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        // 2. 处理注解 @Autowired： 其实就是从bean工厂中获取一个维持的对象，赋值给了某个变量
        for (Field field: declaredFields){
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if(null != autowiredAnnotation){
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if(null != qualifierAnnotation){
                    dependentBeanName = qualifierAnnotation.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                }else {
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }

        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

}
