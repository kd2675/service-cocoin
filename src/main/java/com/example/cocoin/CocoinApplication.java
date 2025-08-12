package com.example.cocoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CocoinApplication {
    public static void main(String[] args) {
        SpringApplication.run(CocoinApplication.class, args);

        String getVersion = org.springframework.core.SpringVersion.getVersion();
        System.out.println("SpringVersion =============> " + getVersion);
    }
}
