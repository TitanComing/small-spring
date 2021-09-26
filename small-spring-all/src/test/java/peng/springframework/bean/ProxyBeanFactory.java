package peng.springframework.bean;

import peng.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by peng on 2021/09/26.
 * <p>
 * 测试自定义对象的代理操作
 */
public class ProxyBeanFactory implements FactoryBean<IUserDao> {

    //这里返回的是一个代理对象，模拟Mybatis的代理
    @Override
    public IUserDao getObject() throws Exception {
        InvocationHandler handler = (proxy, method, args) -> {
            // 添加排除方法
            if ("toString".equals(method.getName())) return this.toString();

            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("10001", "user1");
            hashMap.put("10002", "user2");
            hashMap.put("10003", "user3");

            return "你被代理了 " + method.getName() + "：" + hashMap.get(args[0].toString());
        };
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
