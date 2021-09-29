package peng.springframework.aop;

import java.lang.reflect.Method;

/**
 * Create by peng on 2021/09/29.
 *
 * 切点方法匹配器：判断代理是否要拦截某个方法
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);

}
