package peng.springframework.event;

import peng.springframework.context.ApplicationListener;

import java.util.Date;

/**
 * Create by peng on 2021/09/27.
 *
 * 自己定义的事件自己要定义接受类，要不然没有意义
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        //框架会调用这个方法通知
        System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
    }
}
