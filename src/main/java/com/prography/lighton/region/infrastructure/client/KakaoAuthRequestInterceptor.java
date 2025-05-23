package com.prography.lighton.region.infrastructure.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoAuthRequestInterceptor implements RequestInterceptor {

    @Value("${kakao.local.rest-api-key}")
    private String restApiKey;

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "KakaoAK " + restApiKey);
        template.header("Content-Type", "application/json");
    }
}
