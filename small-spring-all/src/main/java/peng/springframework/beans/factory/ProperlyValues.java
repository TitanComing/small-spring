package peng.springframework.beans.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by peng on 2021/9/14.
 * 实例对象的域信息集合
 */
public class ProperlyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    //这里实际返回的是新数组，防止返回原来数组后被修改
    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for(PropertyValue pv : this.propertyValueList){
            if(pv.getName().equals(propertyName)){
                return pv;
            }
        }
        return null;
    }

}
