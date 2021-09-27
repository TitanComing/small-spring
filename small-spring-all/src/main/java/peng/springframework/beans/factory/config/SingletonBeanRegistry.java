package peng.springframework.beans.factory.config;

/**
 * Create by peng on 2021/9/9.
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);

}
