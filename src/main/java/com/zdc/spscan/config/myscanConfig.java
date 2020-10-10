package com.zdc.spscan.config;

import com.zdc.spscan.annotations.CustomerScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@CustomerScan(basePackages="com.zdc.spscan.service")
public class myscanConfig {
}
