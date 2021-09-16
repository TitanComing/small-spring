package peng.springframework.beans.factory.config;

import peng.springframework.beans.factory.ProperlyValues;

/**
 * Create by peng on 2021/9/9.
 * 一个存储数据信息的对象
 */
public class BeanDefinition {

    private Class beanClass;

    private ProperlyValues properlyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        //这个构造条件默认是个空值，没有入参就构造个空对象
        this.properlyValues = new ProperlyValues();
    }

    public BeanDefinition(Class beanClass, ProperlyValues properlyValues){
        this.beanClass = beanClass;
        this.properlyValues = properlyValues != null ? properlyValues : new ProperlyValues();
    }


    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public ProperlyValues getProperlyValues() {
        return properlyValues;
    }

    public void setProperlyValues(ProperlyValues properlyValues) {
        this.properlyValues = properlyValues;
    }
}
