package com.zdc.scan.config;

import com.zdc.scan.annotation.EnableOdfClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableOdfClients(scanPackages = "com.test.scan.impl")
public class ScanConfig {
}
