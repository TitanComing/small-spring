package peng.springframework.context;

import java.util.EventListener;

/**
 * Create by peng on 2021/09/27.
 *
 * 观察者模式三要素之一： 接收者
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    //监听者就是要接收事件：提供一个接口对事件做出相应
    void onApplicationEvent(E event);

}
