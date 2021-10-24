package peng.springframework;

import org.junit.Test;
import peng.springframework.aop.IUserService;
import peng.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Create by peng on 2021/10/24.
 *
 * module-15 测试将代理类的设置整合到bean的生命周期
 * applyBeanPostProcessrosAfterInitialization 方法执行
 */
public class Middle10ApiTest {

    //调整之后的代理类创建，位于beanPostProcessros的afterInitaialization方法之后
    //测试bean对象已经实例化、属性赋值、初始化了，所以返回的代理bean对象能够获取被代理bean对象的属性
    //spring 容器其实维持的是代理类的bean对象，被代理的bean对象没有维持进入容器
    @Test
    public void test_autoProxy(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springAOP3.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());

    }

}
