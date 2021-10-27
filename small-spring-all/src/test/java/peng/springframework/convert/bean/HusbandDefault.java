package peng.springframework.convert.bean;

import java.time.LocalDate;

public class HusbandDefault {

    private String wifiName;

    //注意这个是Integer类型的，才能触发默认带的StringToNumberConverterFactory,如果是int类型，触发不了
    private Integer age;

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Husband{" +
                "wifiName='" + wifiName + '\'' +
                ", age=" + age +
                '}';
    }

}
