package com.zdc.scan.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @program: ovr-common
 * @description: 默认实现
 * @author: xjr
 * @create: 2020-08-14 15:47
 **/
@Slf4j
public class DefaultOdfParseProxy extends AbstractOdfParseProxy {
    @Override
    protected void before(Method interceptorMethod, Object... args) throws Exception {
        log.info("我是前置");
    }

    @Override
    protected void after(Method interceptorMethod, Object... args) throws Exception {
        log.info("我是后置");
    }
}
