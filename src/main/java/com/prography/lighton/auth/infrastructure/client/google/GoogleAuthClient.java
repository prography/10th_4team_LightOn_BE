package com.prography.lighton.auth.infrastructure.client.google;

import com.prography.lighton.auth.presentation.dto.google.GoogleOAuthToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleAuthClient", url = "https://oauth2.googleapis.com")
public interface GoogleAuthClient {

    @PostMapping("/token")
    GoogleOAuthToken getGoogleAccessToken(
            @RequestHeader("Content-Type") String contentType,
            @RequestParam String code,
            @RequestParam String clientId,
            @RequestParam String clientSecret,
            @RequestParam String redirectUri,
            @RequestParam String grantType
    );


}
