package com.prography.lighton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LightonApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightonApplication.class, args);
    }

}
