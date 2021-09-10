package peng.springframework;

import org.junit.Test;
import peng.springframework.bean.UserService;
import peng.springframework.beans.factory.config.BeanDefinition;
import peng.springframework.beans.factory.support.DefaultListableBeanFactory;

public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3.第一次获取 bean, 这时候会创建一个userservice的实例对象
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();

        // 4.第二次获取 bean from Singleton,这时候缓存的就是上次创建的实例对象
        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
        userService_singleton.queryUserInfo();

    }
}