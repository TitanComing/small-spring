package peng.springframework.aop;

/**
 * Create by peng on 2021/9/29.
 *
 * 切点的类过滤器：判断代理是否要拦截某个类
 */
public interface ClassFilter {

    //切面是否应该作用到某个类上
    boolean matches(Class<?> clazz);

}
