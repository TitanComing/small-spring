package peng.springframework.annotation;

import peng.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "peng");
        hashMap.put("10002", "miao");
        hashMap.put("10003", "wang");
    }


    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}
