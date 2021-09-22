package peng.springframework.context.support;

import peng.springframework.beans.factory.support.DefaultListableBeanFactory;
import peng.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Create by peng on 2021/09/22.
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    //这个层级具体实现了 BeanDefinition 的处理,但是将资源的具体解析往下抛了
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        //这里构造器传入了this, 把自己这个对象传入进去了，然后自己这个对象是 DefaultResourceLoader 的一个子类
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
