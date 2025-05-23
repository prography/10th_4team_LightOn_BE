package com.prography.lighton.auth.infrastructure.client.google;

import static com.prography.lighton.common.constant.AuthConstants.HEADER_CONTENT_TYPE;

import com.prography.lighton.auth.presentation.dto.google.GoogleOAuthToken;
import com.prography.lighton.common.config.FeignRetryConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleAuthClient", url = "https://oauth2.googleapis.com",
        configuration = {FeignRetryConfig.class})
public interface GoogleAuthClient {

    @PostMapping("/token")
    GoogleOAuthToken getGoogleAccessToken(
            @RequestHeader(HEADER_CONTENT_TYPE) String contentType,
            @RequestParam String code,
            @RequestParam String clientId,
            @RequestParam String clientSecret,
            @RequestParam String redirectUri,
            @RequestParam String grantType
    );
}
