package peng.springframework.context;

import java.util.EventObject;

/**
 * Create by peng on 2021/09/27.
 *
 * 观察者模式三要素之一： 事件
 *
 * 注意这个抽象类
 * 抽象类和普通类的区别就在于，抽象类不能被实例化，就是不能被new出来，即使抽象类里面没有抽象方法。
 * 抽象类的作用在于子类对其的继承和实现，也就是多态；
 * 而没有抽象方法的抽象类的存在价值在于：实例化了没有意义，因为类已经定义好了，不能改变其中的方法体，
 * 但是实例化出来的对象却满足不了要求，只有继承并重写了他的子类才能满足要求。所以才把它定义为没有抽象方法的抽象
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
