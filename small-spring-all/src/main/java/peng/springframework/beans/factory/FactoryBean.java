package peng.springframework.beans.factory;

/**
 * Create by peng on 2021/09/26.
 *
 * 工厂bean接口，这个接口用于spring框架和其他框架融合，提供spring生态
 * 本质上是对其他框架暴漏了设置beanDefinition和获取bean实例的接口，这个接口和spring本身通过xml解析是一个道理
 * 注意：暴露出来的这个接口，是第三方框架要实现给spring调用的。和Aware类一样，是属于预先的埋点
 */
public interface FactoryBean<T> {
    //获取对象
    T getObject() throws Exception;
    //获取对象类型
    Class<?> getObjectType();
    //是否单例对象
    boolean isSingleton();
}
