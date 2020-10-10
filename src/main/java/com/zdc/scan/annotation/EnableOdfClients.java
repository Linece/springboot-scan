package com.zdc.scan.annotation;

import com.zdc.scan.proxy.AbstractOdfParseProxy;
import com.zdc.scan.proxy.DefaultOdfParseProxy;
import com.zdc.scan.registry.GlobalOdfBeanRegistry;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.annotation.*;

/**
 * @program: ovr-common
 * @description: odf全局启用注册类
 * @author: xjr
 * @create: 2020-08-14 11:00
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GlobalOdfBeanRegistry.class)
public @interface EnableOdfClients {

    /*
     *被@odfBean注解的接口所在的包位置
    */
    @AliasFor("value")
    String[] scanPackages() default {};

    /*
     *同scanPackages
     */
    @AliasFor("scanPackages")
    String[] value() default {};

    /*
     *是否记录每一次的生成日志  true 是  false （默认在项目根部生成一个日志文件,如果所调用的odfbean是远程模板的方式，则会向远程模板平台发送日志记录请求）
     */
    boolean logRecord() default false;

    /*
     *扫描的bean是否单例
     */
    boolean singleton() default true;

    /*
     *发生异常的时候异常回调处理器
     */
    Class<? extends ExceptionHandler> exceptionHandler() default ExceptionHandler.class;

    /*
     *默认实现代理
     */
    Class<? extends AbstractOdfParseProxy> proxyClass() default DefaultOdfParseProxy.class;




}
