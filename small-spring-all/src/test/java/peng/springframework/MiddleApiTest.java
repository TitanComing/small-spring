package peng.springframework;

import cn.hutool.core.io.IoUtil;
import org.junit.Before;
import org.junit.Test;
import peng.springframework.bean.UserService;
import peng.springframework.beans.factory.support.DefaultListableBeanFactory;
import peng.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import peng.springframework.core.io.DefaultResourceLoader;
import peng.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Create by peng on 2021/09/17.
 */
public class MiddleApiTest {

    //通过配置文件灵活注册bean
    private DefaultResourceLoader resourceLoader;

    @Before
    public void init(){
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test_file() throws IOException {
        Resource resource = resourceLoader.getResource("src/test/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_url() throws IOException {
        Resource resource = resourceLoader.getResource("https://github.com/TitanComing/small-spring/blob/main/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_xml() {
        // 1.初始化 BeanFactory
        // DefaultListableBeanFactory实现了 BeanDefinitionRegistry 接口，所以可以被构造
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. 获取Bean对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

}
