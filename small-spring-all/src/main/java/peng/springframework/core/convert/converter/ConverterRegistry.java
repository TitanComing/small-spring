package peng.springframework.core.convert.converter;

/**
 * Create by peng on 2021/10/26.
 *
 * 类型转换注册接口
 */
public interface ConverterRegistry {

    // 增加转换器
    void addConverter(Converter<?,?> converter);

    //

}
