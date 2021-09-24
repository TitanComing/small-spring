package peng.springframework.bean;

import peng.springframework.beans.BeansException;
import peng.springframework.beans.factory.BeanClassLoaderAware;
import peng.springframework.beans.factory.BeanFactory;
import peng.springframework.beans.factory.BeanFactoryAware;
import peng.springframework.beans.factory.BeanNameAware;
import peng.springframework.context.ApplicationContext;
import peng.springframework.context.ApplicationContextAware;

/**
 * Create by peng on 2021/09/24.
 */
public class UserServiceAware implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    private String uId;
    private String company;
    private String location;
    private UserDao userDao;



    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("Aware获取了到了ClassLoader：" + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Aware获取了到了BeanName：" + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String queryUserInfo() {
        return userDao.queryUserName(uId) + "," + company + "," + location;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
