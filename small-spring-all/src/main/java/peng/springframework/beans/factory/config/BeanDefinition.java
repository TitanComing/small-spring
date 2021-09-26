package peng.springframework.beans.factory.config;

import peng.springframework.beans.PropertyValues;

/**
 * Create by peng on 2021/9/9.
 * 一个存储数据信息的对象
 */
public class BeanDefinition {

    //增加bean的生成机制控制
    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETION;

    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    //bean对应的类信息
    private Class beanClass;

    //bean要填充的属性信息
    private PropertyValues propertyValues;

    //初始化方法
    private String initMethodName;

    //销毁方法
    private String destroyMethodName;

    //默认是单例模式
    private String scope = SCOPE_SINGLETON;
    //默认是单例模式
    private boolean singleton = true;

    private boolean prototype = false;

    public BeanDefinition(Class beanClass) {
        //创建方法整合
        this(beanClass, null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    //这里的原型信息是作为附属信息进行提供的
    public void setScope(String scope){
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
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
