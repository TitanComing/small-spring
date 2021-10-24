package peng.springframework.aop.framework.autoproxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import peng.springframework.aop.*;
import peng.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import peng.springframework.aop.framework.ProxyFactory;
import peng.springframework.beans.BeansException;
import peng.springframework.beans.PropertyValues;
import peng.springframework.beans.factory.BeanFactory;
import peng.springframework.beans.factory.BeanFactoryAware;
import peng.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import peng.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Create by peng on 2021/10/14.
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware{

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException{
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    //实例化之前要进行的操作
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        //放开对基础类的代理
        if(isInfrastructureClass(bean.getClass())) return bean;

        //获取所有相关的切面类
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        for(AspectJExpressionPointcutAdvisor advisor: advisors){
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 过滤匹配类
            if(!classFilter.matches(bean.getClass())) continue;

            //todo 这里其实一个bean对象，可能有多个拦截器，其实应该定义下拦截器的执行顺序
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            return new ProxyFactory(advisedSupport).getProxy();
        }

        return bean;
    }


    private boolean isInfrastructureClass(Class<?> beanClass){
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }
}