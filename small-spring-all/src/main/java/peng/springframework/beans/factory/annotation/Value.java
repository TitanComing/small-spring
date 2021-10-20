package peng.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * Create by peng on 2021/10/20.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface Value {

    //这个地方应该是个表达式："#{systemProperties.myProp}" 这种的
    String value();

}
