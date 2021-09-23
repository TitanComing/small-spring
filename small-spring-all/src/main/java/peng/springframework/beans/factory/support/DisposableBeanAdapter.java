package peng.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.DisposableBean;
import peng.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * Create by peng on 2021/09/23.
 *
 * 对销毁方法的一个适配，因为销毁可以有多个方法
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;

    private final String beanName;

    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition){
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        //1. 接口调用
        if(bean instanceof DisposableBean){
            ((DisposableBean) bean).destroy();
        }

        //2. 注解配置 destroy-method {判断是为了避免二次执行销毁}
        if(StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))){
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }

    }
}
