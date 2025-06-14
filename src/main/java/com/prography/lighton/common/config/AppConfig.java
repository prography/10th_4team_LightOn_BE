package com.prography.lighton.common.config;

import com.prography.lighton.auth.infrastructure.sms.SmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class AppConfig {
}
