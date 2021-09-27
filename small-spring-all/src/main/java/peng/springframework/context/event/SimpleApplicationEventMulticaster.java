package peng.springframework.context.event;

import peng.springframework.beans.factory.BeanFactory;
import peng.springframework.context.ApplicationEvent;
import peng.springframework.context.ApplicationListener;

/**
 * Create by peng on 2021/09/27.
 *
 * 事件广播站的一个简单实现
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    //是为了方便实现BeanFactoryAware接口
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for(final ApplicationListener listener: getApplicationListeners(event)){
            listener.onApplicationEvent(event);
        }
    }
}
