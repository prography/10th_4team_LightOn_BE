package com.prography.lighton.auth.infrastructure.client.google;

import static com.prography.lighton.common.constant.AuthConstants.HEADER_AUTHORIZATION;

import com.prography.lighton.auth.presentation.dto.google.GoogleUser;
import com.prography.lighton.common.config.FeignRetryConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "googleApiClient", url = "https://www.googleapis.com",
        configuration = {FeignRetryConfig.class})
public interface GoogleApiClient {

    @GetMapping("/oauth2/v2/userinfo")
    GoogleUser getGoogleUserInfo(@RequestHeader(HEADER_AUTHORIZATION) String authorization);
}
