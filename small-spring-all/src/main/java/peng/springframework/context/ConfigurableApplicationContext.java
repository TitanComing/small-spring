package peng.springframework.context;

import peng.springframework.beans.BeansException;

/**
 * Create by peng on 2021/09/18.
 * 继承应用上下文，提供了关键的refresh方法
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 提供了关键的刷新容器的方法
     */
    void refresh() throws BeansException;

}
