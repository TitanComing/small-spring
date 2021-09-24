package peng.springframework.context;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.Aware;

/**
 * Create by peng on 2021/09/24.
 * <p>
 * 用户获取应用上下文
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
