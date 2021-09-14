package peng.springframework.beans.factory;

/**
 * Create by peng on 2021/9/14.
 * 属性类，bean工厂创建创建实例对象的时候对域进行填充
 */
public class PropertyValue {
    //都是final修饰，直接就是确定值
    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
