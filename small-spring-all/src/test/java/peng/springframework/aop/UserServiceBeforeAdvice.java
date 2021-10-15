package peng.springframework.aop;

import java.lang.reflect.Method;

/**
 * Create by peng on 2021/10/15.
 * <p>
 * 拦截测试方法
 */
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法： " + method.getName());
    }
}
