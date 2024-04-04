package com.example.cocoin.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.example.cocoin"})
@EnableAspectJAutoProxy
public class AopConfig {
}
