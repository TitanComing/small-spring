package peng.springframework.common;

import peng.springframework.bean.UserService;
import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Create by peng on 2021/09/22.
 */
public class TestBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("改为：shanghai");
        }
        return bean;
    }

    //后置就不做处理了
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
