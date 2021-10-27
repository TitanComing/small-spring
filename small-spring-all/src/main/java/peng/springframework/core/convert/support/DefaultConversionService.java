package peng.springframework.core.convert.support;

import peng.springframework.core.convert.converter.ConverterRegistry;

/**
 * Create by peng on 2021/10/26.
 */
public class DefaultConversionService extends GenericConversionService {

    // 这个地方的构造方法调用了一个自己的静态方法，用来完成对对象的初始化
    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        // 添加各类类型转换工厂
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }

}
