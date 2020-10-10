package com.zdc.scan.registry;

import com.zdc.scan.proxy.AbstractOdfParseProxy;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.util.Assert.notNull;

/**
 * @program: ovr-common
 * @description: odf扫描注解配置类
 * @author: xjr
 * @create: 2020-08-14 14:54
 **/
@Setter
public class OdfScannerConfigurer implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {

    private String basePackage;

    private boolean singleton;

    private boolean logRecord;

    Class<? extends ExceptionHandler> exceptionHandler;

    private String beanName;

    private ApplicationContext applicationContext;

    private Class<? extends AbstractOdfParseProxy> proxyClass;




    @Override
    public void setBeanName(String s) {
        this.beanName=s;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.basePackage,"odf cannot finish installation of bean without the basePackage!");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        OdfClasspathScanner scanner=new OdfClasspathScanner(beanDefinitionRegistry);
        scanner.setSingleton(singleton);
        scanner.setLogRecord(logRecord);
        scanner.setProxyClass(proxyClass);
        scanner.setExceptionHandler(exceptionHandler);
        scanner.registerFilters();
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
