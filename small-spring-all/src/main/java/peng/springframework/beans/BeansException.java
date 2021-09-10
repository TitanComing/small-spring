package peng.springframework.beans;

/**
 * Create by peng on 2021/9/9.
 * beans相关异常
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
