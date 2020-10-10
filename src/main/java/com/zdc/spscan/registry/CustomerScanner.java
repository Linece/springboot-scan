package com.zdc.spscan.registry;

import com.zdc.spscan.annotations.MyService;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class CustomerScanner extends ClassPathBeanDefinitionScanner {

	public CustomerScanner(BeanDefinitionRegistry registry){
			super(registry,false);
	}

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		//添加过滤条件，这里是只添加了@MyService的注解才会被扫描到
		addIncludeFilter(new AnnotationTypeFilter(MyService.class));
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		return beanDefinitions;
	}
}
