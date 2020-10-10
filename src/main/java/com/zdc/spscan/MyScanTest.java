package com.zdc.spscan;

import com.zdc.spscan.config.myscanConfig;
import com.zdc.spscan.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyScanTest {
  public static void main(String[] args) {
	  AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(myscanConfig.class);

	  UserService bean = context.getBean(UserService.class);
	  bean.aa();
  }
}
