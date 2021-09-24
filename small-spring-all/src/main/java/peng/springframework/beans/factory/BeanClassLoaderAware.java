package peng.springframework.beans.factory;

/**
 * Create by peng on 2021/09/24.
 *
 * 传递BeanClassLoader
 */
public interface BeanClassLoaderAware extends Aware{

    void setBeanClassLoader(ClassLoader classLoader);

}
