package com.zdc.scan.proxy;

import com.zdc.scan.annotation.OdfBean;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: ovr-common
 * @description: odf代理抽象类
 * @author: xjr
 * @create: 2020-08-14 11:36
 **/
@Setter
@Getter
@Slf4j
public abstract class AbstractOdfParseProxy implements InvocationHandler {

    protected OdfBean odfBean;

    protected boolean logRecord;

    protected ExceptionHandler exceptionHandler;

//    @Autowired
//    protected BeetlUtils beetlUtils;
//
//    @Autowired
//    protected BizEventUnitManager bizEventUnitManager;
//
//    @Autowired
//    protected OdfModel model;

    @Autowired
    protected ApplicationContext applicationContext;

    private final ThreadLocal<StopWatch> clock=ThreadLocal.withInitial(StopWatch::new);


//    @Autowired
//    OdfMessageScheduleImpl odfMessageSchedule;

    @Autowired
    ThreadPoolTaskExecutor executor;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//        if (method.getDeclaringClass()== OdfMessage.class){
//            if (method.getName().equals("getAnnotation")){
//                return this.odfBean;
//            }
//            before(method,args);
//            try {
//                doCreateOdf(args);
//            }catch (BizException ex){
//                exceptionThrowing(ex);
//            }
//
//            after(method,args);
//            return new BodyType();
//        }
        return null;
    }

    protected abstract void before(Method interceptorMethod,Object ... args) throws Exception;

    protected  abstract  void after(Method interceptorMethod,Object ... args) throws Exception;
//
//    final void exceptionThrowing(BizException ex) throws Throwable{
//        exceptionHandler.handle(ex,odfBean.purpose());
//    }

//    final BodyType doCreateOdf(Object[] args) throws BizException, IOException {
//        boolean success = false;
//        BodyType result;
//        StringBuilder logResult=new StringBuilder();
//        if (args==null || args.length==0){
//            throw new BizException("odf parse方法无效入参,参数全部为空！！");
//        }
//        File file=new File(System.getProperty("user.dir")+File.separator+model.getGenLogPath()+File.separator+"log.txt");
//        if (!file.exists()){
//            file.getParentFile().mkdirs();
//            file.createNewFile();
//        }
//        String fileName = RSCUtils.dataFileName(args[0].toString(), "", args[1].toString(), "", (StringUtils.isBlank(args[0].toString())? null:bizEventUnitManager.getOne(new LambdaQueryWrapper<BizEventUnit>().eq(BizEventUnit::getRscUnitCode, args[0]),false)).getResultStatus());
//        clock.get().start(odfBean.purpose()+"odf数据组装");
//        TemplateDTO templateDTO=odfBean.dependsOn().genTemplateDto(applicationContext,odfBean);
//        templateDTO.setGenerateFileName(String.format(fileName, 1));
//        Object[] params=new Object[3];
//        BizEventUnit eventUnit= Optional.<BizEventUnit>ofNullable(bizEventUnitManager.getOne(Wrappers.<BizEventUnit>lambdaQuery().eq(BizEventUnit::getUnitLevel, UnitLevelEnum.DISCIPLINE.getCode()),false)).orElseThrow(
//                ()->new BizException("根据传入的rscUnitCode找不到对应的比赛相关数据,请确认是否为有效参数")
//        );
//        params[0]=args[0];
//        params[1]=args[1];
//        params[2]=eventUnit;
//        try {
//            //这一块作为以后的动态编译的预留
////            result=((OdfMessage)(Class.forName("com.onesports.competition.ovr.common.core.service.odf.impl.Odf"+args[1]+"Impl").newInstance())).parse(params[0].toString(),params[1].toString(), (BizEventUnit) params[2]);
//            Future<BodyType> asyncResult=executor.submit(()->
//                 odfMessageSchedule.parse(params[0].toString(),params[1].toString(), (BizEventUnit) params[2])
//            );
//            result=asyncResult.get();
//            clock.get().stop();
//            log.info("{}耗时{}ms",clock.get().getLastTaskName(),clock.get().getTotalTimeMillis());
//            beetlUtils.rendToWriter(asyncResult.get(),templateDTO.getXmlContent(),model.getParseTargetPathPrefix(),templateDTO.getGenerateFileName());
//            success=true;
//        }catch (ExecutionException ex){
//            log.error("{}odf数据组装发生异常,异常原因是:{}",odfBean.purpose(),ex.getMessage());
//            throw  new BizException(ex.getMessage());
//        }catch (Exception ex){
//            log.error("odf生成报文发生异常,异常原因是:{}",ex.getMessage());
//            throw  new BizException(ex.getMessage());
//        }
//        finally {
//            FileUtils.writeLines(file, Lists.newArrayList(logResult.append("时间:").append(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).append("  目标:").append("  "+odfBean.purpose()).append("   "+"odf数据组装").append("    "+"耗时").append("    "+clock.get().getTotalTimeMillis()).append("    "+"毫秒").append("    "+"状态:").append("    "+(success?"成功":"失败"))),true);
//            clock.remove();
//        }
//        return result;
   // }



}
