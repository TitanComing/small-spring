package peng.springframework.beans.factory;

import peng.springframework.beans.BeansException;

/**
 * Create by peng on 2021/10/25.
 *
 * 对工厂对象进行抽象，方便放入对应的缓存层级
 */
public interface ObjectFactory<T> {

    T getObject() throws BeansException;

}
