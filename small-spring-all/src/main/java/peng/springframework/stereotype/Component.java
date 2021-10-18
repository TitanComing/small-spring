package peng.springframework.stereotype;

import java.lang.annotation.*;

/**
 * Create by peng on 2021/10/18.
 *
 * 自动扫描装配注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";

}
