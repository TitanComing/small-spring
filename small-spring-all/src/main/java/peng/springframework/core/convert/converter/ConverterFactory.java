package peng.springframework.core.convert.converter;

/**
 * Create by peng on 2021/10/26.
 *
 * 转换器工厂-返回的是一个转换器
 */
public interface ConverterFactory<S, R> {

    /**
     * Get the converter to convert from S to target type T, where T is also an instance of R.
     * @param <T> the target type
     * @param targetType the target type to convert to
     * @return a converter from S to T
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);

}
