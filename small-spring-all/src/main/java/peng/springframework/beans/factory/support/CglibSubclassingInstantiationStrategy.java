package peng.springframework.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * Create by peng on 2021/9/10.
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (null == ctor) return enhancer.create();
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
