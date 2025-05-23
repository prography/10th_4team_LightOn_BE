package com.prography.lighton.auth.infrastructure.client.kakao;

import com.prography.lighton.auth.presentation.dto.kakao.KaKaoUser;
import com.prography.lighton.common.config.FeignRetryConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoApiClient", url = "https://kapi.kakao.com",
        configuration = {FeignRetryConfig.class})
public interface KaKaoApiClient {

    @GetMapping("/v2/user/me")
    KaKaoUser getKaKaoUserInfo(
            @RequestHeader("Authorization") String accessToken,
            @RequestHeader("Content-Type") String contentType);
}
