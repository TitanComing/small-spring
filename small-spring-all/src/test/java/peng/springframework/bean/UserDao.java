package peng.springframework.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "peng");
        hashMap.put("10002", "miao");
        hashMap.put("10003", "wang");
    }

    public void initMethod(){
        System.out.println("执行UserDao初始化方法： hello, 我是初始化方法");
    }

    public void destroyMethod(){
        System.out.println("执行UserDao销毁方法： hello, 我是销毁方法");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}
