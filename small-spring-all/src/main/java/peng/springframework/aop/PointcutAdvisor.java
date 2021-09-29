package peng.springframework.aop;

/**
 * Create by peng on 2021/9/29.
 * <p>
 * 整合了切点和通知，也就组合了一个切面的总和
 */
public interface PointcutAdvisor extends Advisor {

    //返回调用通知动作的切点位置
    Pointcut getPointcut();
}
