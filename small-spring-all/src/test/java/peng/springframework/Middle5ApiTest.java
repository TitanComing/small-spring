package peng.springframework;

import org.junit.Test;
import peng.springframework.context.support.ClassPathXmlApplicationContext;
import peng.springframework.event.CustomEvent;

/**
 * Create by peng on 2021/09/27.
 *
 * 容器事件通知测试
 */
public class Middle5ApiTest {

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springEvent.xml");

        //自定义的事件要调用applicationContext的publishEvent接口发布，目前框架没有提供所有的事件发布方法
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "我是一个自定义的事件！"));

        //框架本身提供的事件，自己定义好自己的监听器就好了。但是关闭事件要注册钩子，因为在close方法中发布的。
        applicationContext.registerShutdownHook();
    }

}
