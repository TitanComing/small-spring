package peng.springframework.context.support;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.config.BeanPostProcessor;
import peng.springframework.context.ApplicationContext;
import peng.springframework.context.ApplicationContextAware;

/**
 * Create by peng on 2021/09/24.
 *
 * 配合应用上下文的获取
 * 主要是应用上下文的获取并不是在在创建Bean的时候进行的,所以包装成一个BeanPostProcessor,在这个里边设置
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
