package peng.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * Create by peng on 2021/10/18.
 *
 * 定义bean作用域注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default "singletion";

}
