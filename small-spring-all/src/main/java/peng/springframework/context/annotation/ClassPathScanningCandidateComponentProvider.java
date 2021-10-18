package peng.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import peng.springframework.beans.factory.config.BeanDefinition;
import peng.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Create by peng on 2021/10/18.
 *
 * 扫描配置文件路径下的被Component注解的类
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage){
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        // 这个地方hutool的ClassUtil的工具类还是挺好用的
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for(Class<?> clazz: classes){
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }

}
