package peng.springframework.event;

import peng.springframework.context.ApplicationListener;
import peng.springframework.context.event.ContextClosedEvent;

/**
 * Create by peng on 2021/09/27.
 *
 * 也可以监听容器已经定义好的事件-这里是ContextClosedEvent
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("关闭事件：" + this.getClass().getName());
    }
}
