package peng.springframework.convert.bean;

import java.time.LocalDate;

public class Husband {

    private String wifiName;

    private LocalDate marriageDate;

    private int age;

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public LocalDate getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(LocalDate marriageDate) {
        this.marriageDate = marriageDate;
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
                ", marriageDate=" + marriageDate +
                ", age=" + age +
                '}';
    }

}
