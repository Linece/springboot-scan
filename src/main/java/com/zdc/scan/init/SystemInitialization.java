package com.zdc.scan.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @program: socket-actuator-spring-boot-starter
 * @description: 系统初始化执行任务
 * @author: xjr
 * @create: 2020-06-24 17:03
 **/
@Component("systemInitialization")
@Slf4j
public class SystemInitialization implements CommandLineRunner {
    private List<GlobalApplicationInit> globalApplicationInits;

    @Autowired
    //@Qualifier("commonThreadPool")
    private Executor threadPoolTaskExecutor;

    /*
    秒表
     */
    private StopWatch stopWatch=new StopWatch();

    public SystemInitialization(@Autowired(required = false) List<GlobalApplicationInit> globalApplicationInits){
        this.globalApplicationInits=globalApplicationInits;
    }
    @Override
    public void run(String... args) throws Exception {
        log.info("class:{},global init task starts",this.getClass().getName());
        if (this.globalApplicationInits!=null){
            this.globalApplicationInits
                    .stream()
                    .sorted(Comparator.comparing(GlobalApplicationInit::order))
                    .forEach(data->{
                        stopWatch.start(data.taskName());
                        if (data.async()){
                            threadPoolTaskExecutor.execute(data::init);
                        }else{
                            data.init();
                        }
                        stopWatch.stop();
                    });
            log.info("项目初始化任务运行结束,具体耗时如下:{}",stopWatch.prettyPrint());
        }
    }
}
