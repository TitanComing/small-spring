package peng.springframework.context.support;

import com.sun.istack.internal.Nullable;
import peng.springframework.beans.factory.FactoryBean;
import peng.springframework.beans.factory.InitializingBean;
import peng.springframework.core.convert.ConversionService;
import peng.springframework.core.convert.converter.Converter;
import peng.springframework.core.convert.converter.ConverterFactory;
import peng.springframework.core.convert.converter.ConverterRegistry;
import peng.springframework.core.convert.converter.GenericConverter;
import peng.springframework.core.convert.support.DefaultConversionService;
import peng.springframework.core.convert.support.GenericConversionService;

import java.util.Set;

/**
 * Create by peng on 2021/10/27.
 * <p>
 * 提供创建 ConversionService 工厂
 * <p>
 * 这里体现了工厂类的用法：创建工厂类之后，可以把自己的对象嵌入到spring的bean生命周期中管理
 * 如果被包装的服务是spring底层的，则是对复杂实现的一个封装；
 * 如果被包装的类是第三方实现的，则是spring框架给与第三方类的一个接口；
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

    @Nullable
    private Set<?> converters;

    @Nullable
    private GenericConversionService conversionService;

    @Override
    public ConversionService getObject() throws Exception {
        return conversionService;
    }

    @Override
    public Class<?> getObjectType() {
        return conversionService.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 这个地方默认构造了一个默认的转化器服务，spring处理bean生命周期，到设置属性这一步会调用
        // 这个加载创建的类是工厂类的一个属性，处理工厂类的时候，调用了getObject()方法，
        // 然后就将需要使用管理的类加载到了spring维持的实例存储中了，就可以使用了。
        this.conversionService = new DefaultConversionService();
        registerConverters(converters, conversionService);
    }

    private void registerConverters(Set<?> converters, ConverterRegistry registry) {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    registry.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    registry.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else {
                    throw new IllegalArgumentException("Each converter object must implement one of the " +
                            "Converter, ConverterFactory, or GenericConverter interfaces");
                }
            }
        }
    }

    public void setConverters(Set<?> converters){
        this.converters = converters;
    }

}
