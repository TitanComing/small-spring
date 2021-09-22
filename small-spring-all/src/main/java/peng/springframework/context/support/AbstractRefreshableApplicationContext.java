package peng.springframework.context.support;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.ConfigurableListableBeanFactory;
import peng.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * Create by peng on 2021/09/22.
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    //todo 这个层级定义的 beanFactory, 但是beanFactory的类型是可以换的，这里还没有预留替换入口
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }


    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory(){
        return beanFactory;
    }

}
