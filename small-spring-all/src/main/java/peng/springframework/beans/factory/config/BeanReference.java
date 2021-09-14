package peng.springframework.beans.factory.config;

/**
 * Create by peng on 2021/9/14.
 *
 * bean的域对另外一个对象引用,实际上就是个bean名称
 */
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName){
        this.beanName = beanName;
    }

    public String getBeanName(){
        return beanName;
    }

}
