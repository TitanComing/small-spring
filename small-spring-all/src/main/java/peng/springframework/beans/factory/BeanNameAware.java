package peng.springframework.beans.factory;

/**
 * Create by peng on 2021/09/24.
 *
 * 传递beanName
 */
public interface BeanNameAware extends Aware {

    void setBeanName(String name);

}
