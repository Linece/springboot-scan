package com.zdc;

import com.zdc.scan.config.ScanConfig;
import com.zdc.scan.impl.BusiService;
import com.zdc.spscan.config.myscanConfig;
import com.zdc.spscan.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScanTestt {

  public static void main(String[] args) {
	  AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanConfig.class);
	  BusiService busiService = context.getBean(BusiService.class);
	  busiService.aa();
  }
}
