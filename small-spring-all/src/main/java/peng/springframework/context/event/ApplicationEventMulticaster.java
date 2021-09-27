package peng.springframework.context.event;

import peng.springframework.context.ApplicationEvent;
import peng.springframework.context.ApplicationListener;

/**
 * Create by peng on 2021/09/27.
 *
 * 应用事件广播,也就是把事件发布者和事件监听者的行为整合到一处
 */
public interface ApplicationEventMulticaster {

    //增加监听者
    void addApplicationListener(ApplicationListener<?> listener);

    //移除监听者
    void removeApplicationListener(ApplicationListener<?> listener);

    //广播事件
    void multicastEvent(ApplicationEvent event);
}
