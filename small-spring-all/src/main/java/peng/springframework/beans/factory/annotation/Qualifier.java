package peng.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * Create by peng on 2021/10/20.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Qualifier {

    String value() default "";

}
