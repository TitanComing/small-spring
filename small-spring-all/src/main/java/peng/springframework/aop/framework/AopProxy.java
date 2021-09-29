package peng.springframework.aop.framework;

/**
 * Create by peng on 2021/9/29.
 *
 * aop代理类的抽象实现，对 JDK&Cglib 的实现进行封装
 */
public interface AopProxy {

    Object getProxy();

}
