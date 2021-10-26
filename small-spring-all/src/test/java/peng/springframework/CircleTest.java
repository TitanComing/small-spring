package peng.springframework;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by peng on 2021/10/26.
 *
 * 测试循环依赖的解决：就是把自己先放入缓存中，如果其它类用的了自己，可以从缓存中获取
 */
public class CircleTest {

    private final static Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    public static void main(String[] args) throws Exception{
        System.out.println(getBean(B.class).getA());
        System.out.println(getBean(A.class).getB());
    }

    private static <T> T getBean(Class<T> beanClass) throws Exception{
        System.out.println("当前缓存中维护的bean: " + singletonObjects.keySet().toString());

        String beanName = beanClass.getSimpleName().toLowerCase();
        if(singletonObjects.containsKey(beanName)){
            return (T) singletonObjects.get(beanName);
        }

        // 先实例化独享存入
        Object obj = beanClass.newInstance();
        singletonObjects.put(beanName, obj);
        System.out.println("新创建并放入缓存中对象： " + beanName);
        // 属性填充补全对象
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            Class<?> fieldClass = field.getType();
            String fieldBeanName = fieldClass.getSimpleName().toLowerCase();
            //主要就是这个，缓存里边有可以依赖的对象，就引入，没有就创建放入缓存
            //这个没有对象，首先就把自身放入缓存了，所以其它对象依赖自身，可以在缓存中找到
            field.set(obj, singletonObjects.containsKey(fieldBeanName)? singletonObjects.get(fieldBeanName): getBean(fieldClass));
            field.setAccessible(false);
        }

        return (T) obj;
    }


}

class A {

    private B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}

class B {

    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
