package com.prography.lighton.region.application.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kakao.local")
public record KakaoLocalProperties(String baseUrl, String restApiKey) {

}