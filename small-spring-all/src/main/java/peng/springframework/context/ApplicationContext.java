package peng.springframework.context;

import peng.springframework.beans.factory.HierarchicalBeanFactory;
import peng.springframework.beans.factory.ListableBeanFactory;
import peng.springframework.core.io.ResourceLoader;

/**
 * Create by peng on 2021/09/18.
 * spring中的应用上下文
 * 通过这个应用上下文，封装了bean对象的定义的读取，注册，实例化，同时预留了注册、实例化前后的修改
 * 继承了bean工厂的方法
 * 这个接口的子类提供给用户使用
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
