package peng.springframework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.junit.Test;
import peng.springframework.bean.UserDao;
import peng.springframework.bean.UserService;
import peng.springframework.beans.factory.ProperlyValues;
import peng.springframework.beans.factory.PropertyValue;
import peng.springframework.beans.factory.config.BeanDefinition;
import peng.springframework.beans.factory.config.BeanReference;
import peng.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. UserDao 注册, 先注册进去，才能引用
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3. UserService 设置属性【uId, userDao】
        ProperlyValues properlyValues = new ProperlyValues();
        properlyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        properlyValues.addPropertyValue(new PropertyValue("userDao",new BeanReference("userDao")));

        // 4. UserService 注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, properlyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5. 获取一个userservice的实例对象;
        //   现在可以传入构造参数了
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();

    }

    @Test
    public void test_cglib(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        Object obj = enhancer.create(new Class[]{String.class}, new Object[]{"peng"});
        System.out.println(obj);
    }

    @Test
    public void test_constructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<UserService> userServiceClass = UserService.class;
        Constructor<UserService> declaredConstructor = userServiceClass.getDeclaredConstructor(String.class);
        UserService userService = declaredConstructor.newInstance("peng");
        System.out.println(userService);
    }

    @Test
    public void test_parameterTypes() throws Exception {
        Class<UserService> beanClass = UserService.class;
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        Constructor<?> constructor = declaredConstructors[1];
        Constructor<UserService> declaredConstructor = beanClass.getDeclaredConstructor(constructor.getParameterTypes());
        UserService userService = declaredConstructor.newInstance("peng");
        System.out.println(userService);
    }

}