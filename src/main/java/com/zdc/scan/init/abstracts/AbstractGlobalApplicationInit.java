package com.zdc.scan.init.abstracts;


import com.zdc.scan.init.GlobalApplicationInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @program: socket-actuator-spring-boot-starter
 * @description: 全局初始化工作抽象
 * @author: xjr
 * @create: 2020-06-24 16:46
 **/
@Slf4j
public abstract class   AbstractGlobalApplicationInit implements GlobalApplicationInit, ApplicationContextAware, EnvironmentAware, InitializingBean {

    private static ApplicationContext applicationContext;

    private static Environment environment;

    @Override
    public final void init(){
        log.info("No {} start Initializing-------------------------",this.order());
        try {
            execute(applicationContext,environment);
        }catch (Throwable t){

        }
        log.info("No {} finishing Task------------------------------",this.order());
    }

    /**
     *
     * @description 实际需要实现的业务初始化逻辑
     * @param applicationContext: spring上下文
     * @param environment:环境变量
     * @throws Throwable
     * @author xiejiarong
     * @date 2020年06月24日 16:52
     */
    public abstract void execute(ApplicationContext applicationContext, Environment environment) throws Throwable ;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("init class:{} has finished the init work of bean.",this.getClass().getName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


}
