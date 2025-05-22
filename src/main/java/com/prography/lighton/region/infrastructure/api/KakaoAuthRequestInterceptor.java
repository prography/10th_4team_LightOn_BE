package com.prography.lighton.region.infrastructure.api;

import com.prography.lighton.region.application.config.KakaoLocalProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoAuthRequestInterceptor implements RequestInterceptor {

    private final KakaoLocalProperties props;

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "KakaoAK " + props.restApiKey());
        template.header("Content-Type", "application/json");
    }
}
