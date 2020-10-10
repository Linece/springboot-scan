package com.zdc.scan.registry;

import com.zdc.scan.annotation.OdfBean;
import com.zdc.scan.proxy.AbstractOdfParseProxy;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Set;

/**
 * @program: ovr-common
 * @description: odf接口扫描
 * @author: xjr
 * @create: 2020-08-14 15:43
 **/
@Setter
@Slf4j
public class OdfClasspathScanner extends ClassPathBeanDefinitionScanner {

    private boolean logRecord;

    private boolean singleton;

    private Class<? extends ExceptionHandler> exceptionHandler;

    private Class<? extends AbstractOdfParseProxy> proxyClass;

    public OdfClasspathScanner(BeanDefinitionRegistry registry) {
        super(registry,false);
    }

    /**
     *
     * @description 扫描指定包下继承了OdfMessage和标注了@OdfBean的接口
     * @author xiejiarong
     * @date 2020年08月14日 16:03
     */
    public void registerFilters() {
        addIncludeFilter(new AnnotationTypeFilter(OdfBean.class));
        //这一步十分关键，保证了扫描的组件必须是Odfmessage的子接口，并且标注了@OdfBean注解
//        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
//            String[] interfaces=metadataReader.getClassMetadata().getInterfaceNames();
//            if (interfaces==null ||interfaces.length==0){
//                return true;
//            }
//            boolean flag=true;
//            for (int i = 0; i < interfaces.length; i++) {
//                if (OdfMessage.class.getName().equals(interfaces[i])){
//                    flag=false;
//                }
//            }
//           return flag;
//        });
    }

    /**
     *
     * @description 重写父类的扫描包方法
     * @param basePackages: 扫描的包名
     * @return 满足条件的class列表
     * @author xiejiarong
     * @date 2020年08月14日 16:10
     */
    @SneakyThrows
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(OdfBean.class));
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            log.warn( "No Odf interfaces was found in {} .package. Please check your configuration" ,Arrays.toString(basePackages));
        } else {
            processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }


    /**
     *
     * @description 为符合条件的Beandefinition设置实例化方式
     * @param beanDefinitions: 扫描过后符合条件的beanDefinition列表
     * @author xiejiarong
     * @date 2020年08月14日 16:11
     */
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) throws Exception {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            this.getRegistry().registerBeanDefinition(definition.getBeanClassName(),definition);
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            this.getRegistry().removeBeanDefinition(holder.getBeanName());
            String beanClassName = definition.getBeanClassName();
            Class sourceClass=Class.forName(beanClassName);
            if (sourceClass.isAnnotationPresent(OdfBean.class)){
                OdfBean odfBean= (OdfBean) sourceClass.getAnnotation(OdfBean.class);
                String beanName=odfBean.documentTypes()[0].value();
                log.debug("creating OdfBean with name :{} and interfaceName:{}",beanName,beanClassName);
                definition.setBeanClass(OdfMessageFactoryBean.class);
                definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
                definition.getPropertyValues().add("singleton",this.singleton);
                definition.getPropertyValues().add("logRecord",this.logRecord);
                definition.getPropertyValues().add("exceptionHandler",this.exceptionHandler);
                definition.getPropertyValues().add("proxyClass",this.proxyClass);
                definition.getPropertyValues().add("odfBean",odfBean);
                definition.setLazyInit(false);
                definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                this.getRegistry().registerBeanDefinition(beanName,definition);
            }
            continue;

        }
    }

    /**
     *
     * @description 重写发现组件逻辑，将接口合理化返回
     * @param beanDefinition: 符合条件的beanDefinition
     * @return true 返回  false 不返回
     * @author xiejiarong
     * @date 2020年08月16日 10:33
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }


    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            return false;
        }
    }

}
