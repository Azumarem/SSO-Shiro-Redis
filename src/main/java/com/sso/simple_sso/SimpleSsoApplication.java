package com.sso.simple_sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SimpleSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleSsoApplication.class, args);
    }

}
