package peng.springframework.util;

/**
 * Create by peng on 2021/10/20.
 *
 * 解析字符串的接口-例如解析配置文件中的配置项
 */
public interface StringValueResolver {

    String resolveStringValue(String strVal);

}
