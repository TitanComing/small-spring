package peng.springframework.beans.factory;
import peng.springframework.beans.BeansException;
/**
 * Create by peng on 2021/9/9.
 * bean工厂，存储、生成数据对象
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

}
