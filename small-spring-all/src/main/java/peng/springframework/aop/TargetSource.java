package peng.springframework.aop;

import peng.springframework.util.ClassUtils;

/**
 * Create by peng on 2021/9/29.
 * <p>
 * 被代理的对象
 */
public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    // 返回被代理对象的类型-接口实现
    // spring-aop默认使用接口进行了实现
    public Class<?>[] getTargetClass() {
        Class<?> clazz = this.target.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass(): clazz;
        return clazz.getInterfaces();
    }

    // 返回被代理对象的实例方便调用
    public Object getTarget() {
        return this.target;
    }

}
