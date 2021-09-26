package peng.springframework;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import peng.springframework.bean.UserService;
import peng.springframework.bean.UserServiceProxy;
import peng.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Create by peng on 2021/09/26.
 */
public class Middle4ApiTest {

    //运行模式下，每次获取都会产生新的对象，所以内存位置是不一样的
    @Test
    public void test_prototype() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springProxyBeanFactory.xml");
        applicationContext.registerShutdownHook();

        // 2. 获取Bean对象调用方法
        UserServiceProxy userServiceProxy01 = applicationContext.getBean("userServiceProxy", UserServiceProxy.class);
        UserServiceProxy userServiceProxy02 = applicationContext.getBean("userServiceProxy", UserServiceProxy.class);

        // 3. 配置 scope="prototype/singleton"
        System.out.println(userServiceProxy01);
        System.out.println(userServiceProxy02);

        // 4. 打印十六进制哈希
        System.out.println(userServiceProxy01 + " 十六进制哈希：" + Integer.toHexString(userServiceProxy01.hashCode()));
        System.out.println(userServiceProxy01 + " 十六进制哈希：" + Integer.toHexString(userServiceProxy02.hashCode()));
        //System.out.println(ClassLayout.parseInstance(userServiceProxy02).toPrintable());
    }

    @Test
    public void test_factory_bean() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springProxyBeanFactory.xml");
        applicationContext.registerShutdownHook();
        // 2. 调用代理方法
        UserServiceProxy userServiceProxy = applicationContext.getBean("userServiceProxy", UserServiceProxy.class);
        System.out.println("测试结果：" + userServiceProxy.queryUserInfo());
    }


}
