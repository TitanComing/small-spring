package peng.springframework;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by peng on 2021/9/9.
 * bean工厂，存储、生成数据对象
 * 存储数据的其实就是个hashMap
 */
public class BeanFactory {

    //框架要多线程操作，所以要用线程安全的类
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public Object getBean(String name){
        return beanDefinitionMap.get(name).getBean();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition){
        beanDefinitionMap.put(name, beanDefinition);
    }
}
