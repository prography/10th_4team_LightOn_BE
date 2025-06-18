package com.prography.lighton.auth.infrastructure.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sms")
public record SmsProperties(
        String apiKey,
        String apiSecret,
        String apiUrl,
        String fromNumber
) {
}
