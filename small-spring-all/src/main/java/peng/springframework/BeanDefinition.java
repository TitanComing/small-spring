package peng.springframework;

/**
 * Create by peng on 2021/9/9.
 * 一个存储数据信息的对象
 */
public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean){this.bean = bean;}

    public Object getBean(){
        return bean;
    }
}
