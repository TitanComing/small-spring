package peng.springframework.beans.factory;
import peng.springframework.beans.BeansException;
/**
 * Create by peng on 2021/9/9.
 * bean工厂，存储、生成数据对象
 * 实例对象的构造参数通过args传入
 * 实例对象的域信息通过BeanDefinition传入
 */
public interface BeanFactory {

    //只通过bean名称传递
    Object getBean(String name) throws BeansException;
    //可以传递构建函数了
    Object getBean(String name, Object... args) throws BeansException;
    //按照类型获取bean实例对象
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
    //按照类型获取bean对象
    <T> T getBean(Class<T> requiredType) throws BeansException;
}
