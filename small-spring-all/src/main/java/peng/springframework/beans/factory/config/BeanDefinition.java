package peng.springframework.beans.factory.config;

import peng.springframework.beans.PropertyValues;

/**
 * Create by peng on 2021/9/9.
 * 一个存储数据信息的对象
 */
public class BeanDefinition {

    //bean对应的类信息
    private Class beanClass;

    //bean要填充的属性信息
    private PropertyValues propertyValues;

    //初始化方法
    private String initMethodName;

    //销毁方法
    private String destroyMethodName;


    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        //这个构造条件默认是个空值，没有入参就构造个空对象
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setProperlyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }


    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
