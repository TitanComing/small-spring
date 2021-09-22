package peng.springframework;

import org.junit.Test;
import peng.springframework.bean.UserService;
import peng.springframework.beans.factory.support.DefaultListableBeanFactory;
import peng.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import peng.springframework.common.TestBeanFactoryPostProcessor;
import peng.springframework.common.TestBeanPostProcessor;
import peng.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Create by peng on 2021/09/22.
 */
public class Middle2ApiTest {

    //验证预留接口处理：手动调用bean工厂的创建，并且手动调用接口处理
    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor(){

        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.读取配置文件，加载BeanDefinition信息
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3.尝试修改注册后的BeanDefinition
        TestBeanFactoryPostProcessor  beanFactoryPostProcessor = new TestBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 4.修改实例化之后的bean属性信息
        TestBeanPostProcessor beanPostProcessor = new TestBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        // 5.调用实例化的bean防范
        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }


    // 将上面的方法整合到一个类流程中，就是spring上下文主要的作用
    // 这个过程中，上下文自动处理了接口类，加载了所有符合要求的接口类
    @Test
    public void test_xml() {
        // 1. 初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");

        // 2. 获取Bean对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);

    }




}
