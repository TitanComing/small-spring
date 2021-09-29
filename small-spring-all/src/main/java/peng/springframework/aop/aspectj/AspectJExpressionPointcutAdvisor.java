package peng.springframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import peng.springframework.aop.Pointcut;
import peng.springframework.aop.PointcutAdvisor;

/**
 * Create by peng on 2021/9/29.
 *
 * 切面-整合切点和通知： pointcut-切点； advice-通知；
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    //切面的切点
    private AspectJExpressionPointcut pointcut;
    //切面的方法
    private Advice advice;
    //切面表达式
    private String expression;

    //设置切点表达式，实际上可以通过表达式构造出来一个切点
    public void setExpression(String expression){
        this.expression = expression;
    }

    //设置通知，也就是切面的拦截方法
    public void setAdvice(Advice advice){
        this.advice = advice;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        //这里边有个优先级的控制
        if(null == pointcut){
            pointcut= new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

}
