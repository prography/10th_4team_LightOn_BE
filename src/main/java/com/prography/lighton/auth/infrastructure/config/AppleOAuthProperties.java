package com.prography.lighton.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "apple.oauth")
public class AppleOAuthProperties {
    private String teamId;
    private String clientId;
    private String keyId;
    private String privateKeyPath;
    private String redirectUri;
}