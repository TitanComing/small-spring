package peng.springframework.core.io;

/**
 * Create by peng on 2021/09/17.
 * 资源类加载器，封装用户入参类型
 */
public interface ResourceLoader {
    /**
     * Pseudo URL prefix for loading from the class path: "classpath:"
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);
}
