package peng.springframework;

import org.junit.Test;
import peng.springframework.circulation.Husband;
import peng.springframework.circulation.Wife;
import peng.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Create by peng on 2021/10/26.
 *
 * module16 循环依赖
 */
public class HighLevel1Test {

    @Test
    public void test_circular(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springCircle.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        Wife wife = applicationContext.getBean("wife", Wife.class);
        System.out.println("老公的媳妇：" + husband.queryWife());
        System.out.println("媳妇的老公：" + wife.queryHusband());
    }


}
