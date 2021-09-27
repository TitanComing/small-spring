package peng.springframework.context;

/**
 * Create by peng on 2021/09/27.
 *
 * 观察者模式三要素之一： 发布者
 */
public interface ApplicationEventPublisher {

    //发布者就是要发布事件
    void publishEvent(ApplicationEvent event);

}
