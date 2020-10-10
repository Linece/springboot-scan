package com.zdc.scan.registry;
import com.zdc.scan.annotation.OdfBean;
import com.zdc.scan.enums.DocumentType;
import com.zdc.scan.factory.OdfBeanFactory;
import com.zdc.scan.inter.OdfMessage;
import com.zdc.scan.proxy.AbstractOdfParseProxy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: ovr-common
 * @description: odf实例化工厂bean
 * @author: xjr
 * @create: 2020-08-14 16:18
 **/
@Setter
@Getter
public class OdfMessageFactoryBean implements FactoryBean<OdfMessage>, ApplicationContextAware {

    private boolean singleton;

    private Class<? extends OdfMessage> clazz;

    private boolean logRecord;

    private Class<? extends ExceptionHandler> exceptionHandler;

    private Class<? extends AbstractOdfParseProxy> proxyClass;

    private OdfBean odfBean;

    private ApplicationContext context;

    public static Map<String,OdfMessage> beansMap= new HashMap<>();



    public OdfMessageFactoryBean(Class<OdfMessage> clazz) {
        this.clazz = clazz;
    }

    @Override
    public OdfMessage getObject() throws Exception {
        return newProxyInstance();
    }

    @Override
    public Class<OdfMessage> getObjectType() {
        return OdfMessage.class;
    }

    @Override
    public boolean isSingleton() {
        return this.singleton;
    }

    public OdfMessage newProxyInstance() throws Exception {
        AutowireCapableBeanFactory autowireCapableBeanFactory=this.context.getAutowireCapableBeanFactory();
        AbstractOdfParseProxy proxy=autowireCapableBeanFactory.createBean(proxyClass);
        proxy.setLogRecord(logRecord);
        proxy.setExceptionHandler(exceptionHandler.newInstance());
        proxy.setOdfBean(odfBean);
        OdfMessage proxyObj=(OdfMessage) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{OdfMessage.class},proxy);
        addToBeanMap(proxyObj);
        return proxyObj;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }


    private void addToBeanMap(OdfMessage odfMessage){

            DocumentType[] types=odfBean.documentTypes();
            for (int i = 0; i < types.length; i++) {
                beansMap.put(types[i].value(),odfMessage);
            }
            OdfBeanFactory.beansList.add(odfMessage);
    }
}
