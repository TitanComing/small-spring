package peng.springframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import peng.springframework.aop.MethodBeforeAdvice;

/**
 * Create by peng on 2021/10/14.
 *
 * advice是通过MethodInterceptor发挥作用的(也就有调用advice对象中的方法)
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(){}

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice){
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return methodInvocation.proceed();
    }
}
