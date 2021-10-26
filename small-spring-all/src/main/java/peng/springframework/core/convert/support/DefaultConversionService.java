package peng.springframework.core.convert.support;

import peng.springframework.core.convert.converter.ConverterRegistry;

/**
 * Create by peng on 2021/10/26.
 */
public class DefaultConversionService extends GenericConversionService {

    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        // 添加各类类型转换工厂
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }

}
