package peng.springframework;


import org.junit.Test;
import peng.springframework.annotation.IUserService;
import peng.springframework.annotation.UserDao;
import peng.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 测试注解
 */
public class Middle9ApiTest {

    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springAnnotation.xml");
        UserDao userDao = applicationContext.getBean(UserDao.class);
        IUserService userService = applicationContext.getBean("userServiceAnnotation", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

    @Test
    public void test_scan_propertyPlaceholder() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springAnnotation1.xml");
        UserDao userDao = applicationContext.getBean(UserDao.class);
        IUserService userService = applicationContext.getBean("userServiceAnnotation", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

}
