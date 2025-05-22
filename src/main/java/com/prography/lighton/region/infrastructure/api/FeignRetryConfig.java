package com.prography.lighton.region.infrastructure.api;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FeignRetryConfig {

    /**
     * 최대 3회 재시도, 1st: 200 ms, 2nd: 400 ms, 3rd: 800 ms
     */
    @Bean
    Retryer kakaoRetryer() {
        return new Retryer.Default(200, 800, 3);
    }
}