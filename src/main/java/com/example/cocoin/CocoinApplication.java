package com.example.cocoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
public class CocoinApplication {
    public static void main(String[] args) {
        SpringApplication.run(CocoinApplication.class, args);

        String getVersion = org.springframework.core.SpringVersion.getVersion();
        System.out.println("SpringVersion =============> " + getVersion);
    }
}
