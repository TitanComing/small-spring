package peng.springframework.util;

/**
 * Create by peng on 2021/09/17.
 * 类加载相关工具封装
 */
public class ClassUtils {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // 这里仅仅是catch住，不是所有的cath都需要处理
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
        }
        return cl;
    }

    //判断是不是Cglib生成的代理类
    public static boolean isCglibProxyClass(Class<?> clazz){
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }

    //判断是不是Cglib生成的代理类的名称
    public static boolean isCglibProxyClassName(String className){
        return (className != null && className.contains("$$"));
    }

}
