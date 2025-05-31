package com.prography.lighton.common.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignRetryConfig {

    /**
     * 최대 3회 재시도, 1st: 200 ms, 2nd: 400 ms, 3rd: 800 ms
     */
    @Bean
    Retryer retryer() {
        return new Retryer.Default(200, 800, 3);
    }
}