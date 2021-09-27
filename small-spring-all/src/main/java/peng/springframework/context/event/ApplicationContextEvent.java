package peng.springframework.context.event;

import peng.springframework.context.ApplicationContext;
import peng.springframework.context.ApplicationEvent;

/**
 * Create by peng on 2021/09/27.
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    //说明这个事件是谁抛出的
    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }

}
