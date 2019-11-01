package com.iuzx.sysuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SysuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysuserApplication.class,args);
    }
}
