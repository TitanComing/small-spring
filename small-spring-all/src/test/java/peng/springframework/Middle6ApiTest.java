package peng.springframework;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;
import peng.springframework.aop.*;
import peng.springframework.aop.aspectj.AspectJExpressionPointcut;
import peng.springframework.aop.framework.Cglib2AopProxy;
import peng.springframework.aop.framework.JdkDynamicAopProxy;
import peng.springframework.aop.framework.ReflectiveMethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create by peng on 2021/9/29.
 */
public class Middle6ApiTest {

    @Test
    public void test_aop() throws NoSuchMethodException{
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* peng.springframework.aop.UserService.*(..))");

        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }

    @Test
    public void test_dynamic(){
        // 被代理对象
        IUserService userService = new UserService();

        // 构建切面信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        //这个地方其实是个拦截的自定义的处理方法，也是不自定义，其实就原样执行被代理对象的方法了
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        //现在这个切面定义到了接口类上了
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* peng.springframework.aop.IUserService.*(..))"));

        // 获取生成的代理类对象-这里用jdk的动态代理
        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_jdk.queryUserInfo());

        // 代理对象(Cglib2AopProxy)
        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_cglib.register("花花"));

    }

    @Test
    public void test_proxy_class(){
        IUserService userService = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserService.class},((proxy, method, args) -> "代理了！"));
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    //测试代理模式
    @Test
    public void test_proxy_method(){
        // 被代理的对象
        Object targetObj = new UserService();

        // AOP 代理
        IUserService proxy = (IUserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), new InvocationHandler() {
            //方法匹配器
            MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* peng.springframework.aop.IUserService.*(..))");

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (methodMatcher.matches(method, targetObj.getClass())) {
                    // 方法拦截器
                    MethodInterceptor methodInterceptor = invocation -> {
                        long start = System.currentTimeMillis();
                        try {
                            return invocation.proceed();
                        } finally {
                            System.out.println("监控 - Begin By AOP");
                            System.out.println("方法名称：" + invocation.getMethod().getName());
                            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
                            System.out.println("监控 - End\r\n");
                        }
                    };
                    // 反射调用
                    return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
                }
                return method.invoke(targetObj, args);
            }
        });

        String result = proxy.queryUserInfo();
        System.out.println("测试结果：" + result);

    }

}
