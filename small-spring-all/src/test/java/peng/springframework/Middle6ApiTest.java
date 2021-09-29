package peng.springframework;

import org.junit.Test;
import peng.springframework.aop.IUserService;
import peng.springframework.aop.MethodMatcher;
import peng.springframework.aop.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create by peng on 2021/9/29.
 */
public class Middle6ApiTest {

    //测试代理模式
    @Test
    public void test_proxy_method(){
        // 被代理的对象
        Object targetObj = new UserService();

        // AOP 代理:这里是通过接口实现代理
//        InvocationHandler invocationHandler = new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                // 方法匹配器
//                //MethodMatcher methodMatcher = new
//            }
//        }
//        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(),)


    }

}
