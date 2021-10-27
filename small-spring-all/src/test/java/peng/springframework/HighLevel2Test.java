package peng.springframework;

import org.junit.Test;
import peng.springframework.context.support.ClassPathXmlApplicationContext;
import peng.springframework.convert.bean.Husband;
import peng.springframework.convert.bean.HusbandDefault;
import peng.springframework.convert.converter.StringToIntegerConverter;
import peng.springframework.core.convert.converter.Converter;
import peng.springframework.core.convert.support.StringToNumberConverterFactory;

/**
 * Create by peng on 2021/10/27.
 *
 * 测试类型转换服务
 */
public class HighLevel2Test {

    @Test
    public void test_StringToIntegerConverter() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer num = converter.convert("1234");
        System.out.println("测试结果：" + num);
    }

    @Test
    public void test_StringToNumberConverterFactory() {
        StringToNumberConverterFactory converterFactory = new StringToNumberConverterFactory();

        Converter<String, Integer> stringToIntegerConverter = converterFactory.getConverter(Integer.class);
        System.out.println("测试结果：" + stringToIntegerConverter.convert("1234"));

        Converter<String, Long> stringToLongConverter = converterFactory.getConverter(Long.class);
        System.out.println("测试结果：" + stringToLongConverter.convert("1234"));
    }

    //测试整合到bean生命周期中的转换器
    @Test
    public void test_convert(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springConverter.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        System.out.println("测试结果：" + husband);
    }

    //测试框架自带的转换器
    @Test
    public void test_convert_default(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springConverterDefault.xml");
        HusbandDefault husbandDefault = applicationContext.getBean("husbandDefault", HusbandDefault.class);
        System.out.println("测试结果：" + husbandDefault);
    }

}
