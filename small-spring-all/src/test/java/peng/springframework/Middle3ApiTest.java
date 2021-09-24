package peng.springframework;

import org.junit.Test;
import peng.springframework.bean.UserServiceAware;
import peng.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Create by peng on 2021/09/24.
 */
public class Middle3ApiTest {

    //创建bean对象过程中涉及到的相关信息，只有创建过程中调用相关方法才方便放出，
    //否则很难获取bean创建过程中涉及的相关信息
    @Test
    public void test_xml() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springAware.xml");
        applicationContext.registerShutdownHook();

        // 2. 获取Bean对象调用方法
        UserServiceAware userServiceAware = applicationContext.getBean("userServiceAware", UserServiceAware.class);
        String result = userServiceAware.queryUserInfo();
        System.out.println("测试结果：" + result);

        System.out.println("ApplicationContextAware："+userServiceAware.getApplicationContext());
        System.out.println("BeanFactoryAware："+userServiceAware.getBeanFactory());
    }

}
