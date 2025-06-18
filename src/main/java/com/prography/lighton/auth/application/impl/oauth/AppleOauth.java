package com.prography.lighton.auth.application.impl.oauth;

import static com.prography.lighton.common.constant.AuthConstants.CLIENT_ID;
import static com.prography.lighton.common.constant.AuthConstants.GRANT_TYPE;
import static com.prography.lighton.common.constant.AuthConstants.REDIRECT_URI;
import static com.prography.lighton.common.constant.AuthConstants.RESPONSE_TYPE;
import static com.prography.lighton.common.constant.AuthConstants.SCOPE;

import com.prography.lighton.auth.application.impl.token.AppleOAuthTokenService;
import com.prography.lighton.auth.infrastructure.client.apple.AppleOAuthClient;
import com.prography.lighton.auth.infrastructure.config.AppleOAuthProperties;
import com.prography.lighton.auth.presentation.dto.apple.AppleOAuthToken;
import com.prography.lighton.auth.presentation.dto.apple.AppleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class AppleOauth {

    private static final String APPLE_AUTH_URL = "https://appleid.apple.com/auth/authorize";
    private static final String RESPONSE_TYPE_VALUE = "code id_token";
    private static final String SCOPE_VALUE = "email name";
    private static final String RESPONSE_MODE = "response_mode";
    private static final String RESPONSE_MODE_VALUE = "form_post";

    private final AppleOAuthClient appleOAuthClient;
    private final AppleOAuthTokenService appleOAuthTokenService;
    private final AppleOAuthProperties appleOAuthProperties;

    public String getOauthRedirectURL() {
        return UriComponentsBuilder
                .fromHttpUrl(APPLE_AUTH_URL)
                .queryParam(CLIENT_ID, appleOAuthProperties.getClientId())
                .queryParam(REDIRECT_URI, appleOAuthProperties.getRedirectUri())
                .queryParam(RESPONSE_TYPE, RESPONSE_TYPE_VALUE)
                .queryParam(SCOPE, SCOPE_VALUE)
                .queryParam(RESPONSE_MODE, RESPONSE_MODE_VALUE)
                .build()
                .toUriString();
    }

    public AppleOAuthToken requestAccessToken(String code) {
        return appleOAuthClient.getToken(
                appleOAuthProperties.getClientId(),
                appleOAuthTokenService.createClientSecret(),
                code,
                GRANT_TYPE,
                appleOAuthProperties.getRedirectUri()
        );
    }

    public AppleUser parseUserFromIdToken(AppleOAuthToken tokens) {
        return appleOAuthTokenService.parse(tokens.id_token());
    }
}
