package peng.springframework.context.support;

import peng.springframework.beans.BeansException;

/**
 * Create by peng on 2021/09/22.
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    //构造函数也可以往外抛出异常
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        //因为这个是一路继承下来的，所以模板模式到这个子集基本的方法都有
        refresh();
    }

    //为了便利，传入长度为1的字符串数组
    public ClassPathXmlApplicationContext(String configLocations) throws BeansException{
        this(new String[]{configLocations});
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
