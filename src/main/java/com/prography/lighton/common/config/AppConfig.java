package com.prography.lighton.common.config;

import com.prography.lighton.region.application.config.KakaoLocalProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KakaoLocalProperties.class)
public class AppConfig {
}
