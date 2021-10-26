package peng.springframework.core.convert.converter;

/**
 * Create by peng on 2021/10/26.
 *
 * 转换的类型
 */
public interface Converter<S, T> {

    T convert(S source);

}
