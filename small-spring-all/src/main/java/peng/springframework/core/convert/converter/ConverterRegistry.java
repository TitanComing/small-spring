package peng.springframework.core.convert.converter;

/**
 * Create by peng on 2021/10/26.
 *
 * 类型转换注册接口
 */
public interface ConverterRegistry {

    // 增加转换器
    void addConverter(Converter<?,?> converter);

    // 增加一个通用转换器
    void addConverter(GenericConverter converter);

    // 增加一个转换器工厂
    void addConverterFactory(ConverterFactory<?,?> converterFactory);

}
