package peng.springframework.core.convert.support;

import com.sun.istack.internal.Nullable;
import peng.springframework.core.convert.converter.Converter;
import peng.springframework.core.convert.converter.ConverterFactory;
import peng.springframework.util.NumberUtils;

/**
 * Create by peng on 2021/10/26.
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {
    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<>(targetType);
    }

    private static final class StringToNumber<T extends Number> implements Converter<String, T>{

        private final Class<T> targetType;

        public StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        @Nullable
        public T convert(String source) {
            if(source.isEmpty()){return null;}
            return NumberUtils.parseNumber(source, this.targetType);
        }
    }

}
