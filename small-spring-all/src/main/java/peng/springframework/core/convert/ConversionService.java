package peng.springframework.core.convert;

import com.sun.istack.internal.Nullable;

/**
 * Create by peng on 2021/10/26.
 *
 * 类型转换服务接口
 */
public interface ConversionService {

    // 判断两个类型之间是否能够转换
    boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetType);

    // 将一个对象转换为给定的类型
    <T> T convert(Object source, Class<T> targetType);

}
