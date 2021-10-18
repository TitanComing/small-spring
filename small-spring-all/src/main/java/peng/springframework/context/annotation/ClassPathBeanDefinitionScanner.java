package peng.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import peng.springframework.beans.factory.config.BeanDefinition;
import peng.springframework.beans.factory.support.BeanDefinitionRegistry;
import peng.springframework.stereotype.Component;

import java.util.Set;

/**
 * Create by peng on 2021/10/18.
 *
 * 处理具体扫描出来的类
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider{

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void doScan(String... basePackages){
        for (String basePackage: basePackages){
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for(BeanDefinition beanDefinition : candidates){
                // 对每个类具体解析,填充scope及prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if(StrUtil.isNotEmpty(beanScope)){
                    beanDefinition.setScope(beanScope);
                }
                //这个地方才完成BeanDefinition的注册
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
    }

    private String resolveBeanScope(BeanDefinition beanDefinition){
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if(scope != null){
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    private String determineBeanName(BeanDefinition beanDefinition){
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if(StrUtil.isEmpty(value)){
            //得到类的简写的名称
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }


}
