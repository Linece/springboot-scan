package com.zdc.scan.registry;

import com.zdc.scan.annotation.EnableOdfClients;
import com.zdc.scan.annotation.OdfBean;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: ovr-common
 * @description: 全局odf策略bean生命周期入口
 * @author: xjr
 * @create: 2020-08-14 11:51
 **/
@Slf4j
public class GlobalOdfBeanRegistry implements ImportBeanDefinitionRegistrar,ResourceLoaderAware, BeanFactoryAware {


    private ResourceLoader resourceLoader;

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setResourceLoader(@NonNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     *
     * @description 启动时自动执行
     * @param importingClassMetadata:保存了注解原信息
     * @param registry 动态注册spring bean的注册类
     * @author xiejiarong
     * @date 2020年08月14日 14:51
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        log.info("------------------------global odf initializing task is ready-----------------------------------------");
        AnnotationAttributes annotationAttributes=AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableOdfClients.class.getName()));
        if (annotationAttributes!=null){
            registerBeanDefinitions(annotationAttributes,registry);
        }
    }

    /**
     *
     * @description 真正执行动态注册beanDefinition的入口
     * @param attributes: 启动注解原信息
     * @param registry spring动态注册bean类
     * @return
     * @author xiejiarong
     * @date 2020年08月14日 14:53
     */
    void registerBeanDefinitions(AnnotationAttributes attributes,BeanDefinitionRegistry registry){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(OdfScannerConfigurer.class);
        List<String> basePackages = new ArrayList<>();
        basePackages.addAll(
                Arrays.stream(attributes.getStringArray("value")).filter(StringUtils::hasText).collect(Collectors.toList()));

        basePackages.addAll(Arrays.stream(attributes.getStringArray("scanPackages")).filter(StringUtils::hasText)
                .collect(Collectors.toList()));
         builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(basePackages));
        OdfClasspathScanner scan = new OdfClasspathScanner(registry);
        scan.setBeanNameGenerator(( beanDefinition,beanDefinitionRegistry)->{
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> clz = Class.forName(beanClassName);
                OdfBean at = clz.getAnnotation(OdfBean.class);
                if (null == at) return null;
                //如果@MyService没有指定名字,那么默认首字母小写进行注册
                if (at.value().equalsIgnoreCase("")  ) {
                    String clzSimpleName = clz.getSimpleName();
                    String first = String.valueOf(clzSimpleName.charAt(0));
                    return clzSimpleName.replaceFirst(first,first.toLowerCase());
                }
                return at.value();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
        if(resourceLoader != null){
            scan.setResourceLoader(resourceLoader);
        }
        scan.doScan(StringUtils.toStringArray(basePackages));

         //registry.registerBeanDefinition("aaaaaaaaaaaaaa", builder.getBeanDefinition());
//        builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(basePackages));
//        builder.addPropertyValue("singleton", attributes.getBoolean("singleton"));
//        builder.addPropertyValue("logRecord", attributes.getBoolean("logRecord"));
//        builder.addPropertyValue("exceptionHandler", attributes.getClass("exceptionHandler"));
//        builder.addPropertyValue("proxyClass", attributes.getClass("proxyClass"));
//        registry.registerBeanDefinition("ODF-CONFIGURER",builder.getBeanDefinition());
    }
}
