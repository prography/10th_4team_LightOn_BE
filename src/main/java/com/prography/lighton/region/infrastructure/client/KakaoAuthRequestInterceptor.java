package com.prography.lighton.region.infrastructure.client;

import static com.prography.lighton.common.constant.AuthConstants.HEADER_AUTHORIZATION;
import static com.prography.lighton.common.constant.AuthConstants.HEADER_CONTENT_TYPE;

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
        template.header(HEADER_AUTHORIZATION, "KakaoAK " + restApiKey);
        template.header(HEADER_CONTENT_TYPE, "application/json");
    }
}
