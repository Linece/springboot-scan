package com.zdc;

import com.zdc.scan.config.ScanConfig;
import com.zdc.spscan.config.myscanConfig;
import com.zdc.spscan.service.UserService;
import org.eclipse.jdt.internal.compiler.codegen.AnnotationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScanTest {
  public static void main(String[] args) {
	  AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(myscanConfig.class);
	  UserService userService = context.getBean(UserService.class);
	  userService.aa();
  }
}
