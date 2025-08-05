package com.prography.lighton.auth.infrastructure.client.kakao;

import static com.prography.lighton.common.constant.AuthConstants.HEADER_CONTENT_TYPE;

import com.prography.lighton.auth.presentation.dto.kakao.KaKaoOAuthToken;
import com.prography.lighton.common.config.FeignRetryConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoAuthClient", url = "https://kauth.kakao.com",
        configuration = {FeignRetryConfig.class})
public interface KaKaoAuthClient {

    @PostMapping("/oauth/token")
    KaKaoOAuthToken getKaKaoAccessToken(
            @RequestHeader(HEADER_CONTENT_TYPE) String contentType,
            @RequestParam String grant_type,
            @RequestParam String redirectUri,
            @RequestParam String client_id,
            @RequestParam(defaultValue = "authorization_code") String code);
}
