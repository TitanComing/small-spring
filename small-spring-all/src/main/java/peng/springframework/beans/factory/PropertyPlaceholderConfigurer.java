package peng.springframework.beans.factory;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.PropertyValue;
import peng.springframework.beans.PropertyValues;
import peng.springframework.beans.factory.config.BeanDefinition;
import peng.springframework.beans.factory.config.BeanFactoryPostProcessor;
import peng.springframework.core.io.DefaultResourceLoader;
import peng.springframework.core.io.Resource;
import peng.springframework.util.StringValueResolver;

import java.util.Properties;

/**
 * Create by peng on 2021/10/18.
 * <p>
 * 初始占位符
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    //占位符前缀
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    //占位符后缀
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            // 加载属性文件,properties
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);

            //属性占位符替换
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    //这个地方限制了属性值必须是字符串类型，spring在赋值的时候会自动转换成对应的属性类型
                    if (!(value instanceof String)) continue;
                    value = resolvePlaceholder((String) value, properties);
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
                }
            }

            // 添加字符串解析器， 用于解析@Value注解
            StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
            beanFactory.addEmbeddedValueResolver(valueResolver);

        } catch (Exception e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    private String resolvePlaceholder(String value, Properties properties) {
        String strVal = value;
        StringBuilder buffer = new StringBuilder(strVal);
        int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
            String propKey = strVal.substring(startIdx + 2, stopIdx);
            String propVal = properties.getProperty(propKey);
            buffer.replace(startIdx, stopIdx + 1, propVal);
        }

        return buffer.toString();
    }

    //这个地方定义了一个内部类
    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            // 注意： 这里用到了 类名.class的语法，用于内部类调用外部类的方法
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
        }

    }

    public void setLocation(String location) {
        this.location = location;
    }
}
