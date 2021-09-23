package peng.springframework.beans.factory;

/**
 * Create by peng on 2021/09/23.
 *
 * *InitializingBean、 DisposableBean，两个接口方法还是比较常用的，在一些需要结
 *  合 Spring 实现的组件中，经常会使用这两个方法来做一些参数的初始化和销毁操
 *  作。比如接口暴漏、数据库数据读取、配置文件加载等等
 */
public interface InitializingBean {

    /**
     * 从bean定义中设置完属性后调用
     */
    void afterPropertiesSet() throws Exception;

}
