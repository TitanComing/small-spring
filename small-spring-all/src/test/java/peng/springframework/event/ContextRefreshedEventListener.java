package peng.springframework.event;

import peng.springframework.context.ApplicationListener;
import peng.springframework.context.event.ContextRefreshedEvent;

/**
 * Create by peng on 2021/09/27.
 *
 * 也可以监听容器已经定义好的事件-这里是ContextRefreshedEvent
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
    }
}
