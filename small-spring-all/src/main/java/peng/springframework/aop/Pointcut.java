package peng.springframework.aop;

/**
 * Create by peng on 2021/9/29.
 *
 * 切入点
 * 切面是个拦截的概念，一个切面具体包括：pointcut-切入点，操作发生的地方；advice-操作发生的逻辑
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
