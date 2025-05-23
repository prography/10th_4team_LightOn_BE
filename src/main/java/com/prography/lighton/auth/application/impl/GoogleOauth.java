package com.prography.lighton.auth.application.impl;

import static com.prography.lighton.common.constant.AuthConstants.CLIENT_ID;
import static com.prography.lighton.common.constant.AuthConstants.CONTENT_TYPE;
import static com.prography.lighton.common.constant.AuthConstants.GRANT_TYPE;
import static com.prography.lighton.common.constant.AuthConstants.KEY_VALUE_DELIMITER;
import static com.prography.lighton.common.constant.AuthConstants.QUERY_DELIMITER;
import static com.prography.lighton.common.constant.AuthConstants.QUERY_PREFIX;
import static com.prography.lighton.common.constant.AuthConstants.REDIRECT_URI;
import static com.prography.lighton.common.constant.AuthConstants.RESPONSE_TYPE;
import static com.prography.lighton.common.constant.AuthConstants.RESPONSE_TYPE_CODE;
import static com.prography.lighton.common.constant.AuthConstants.SCOPE;
import static com.prography.lighton.common.constant.JwtConstants.BEARER_PREFIX;

import com.prography.lighton.auth.infrastructure.client.google.GoogleApiClient;
import com.prography.lighton.auth.infrastructure.client.google.GoogleAuthClient;
import com.prography.lighton.auth.presentation.dto.google.GoogleOAuthToken;
import com.prography.lighton.auth.presentation.dto.google.GoogleUser;
import com.prography.lighton.common.feign.SafeFeignExecutor;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleOauth {

    @Value("${spring.oauth2.google.url}")
    private String GOOGLE_SNS_URL;
    @Value("${spring.oauth2.google.client-id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${spring.oauth2.google.callback-login-url}")
    private String GOOGLE_SNS_CALLBACK_LOGIN_URL;
    @Value("${spring.oauth2.google.client-secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${spring.oauth2.google.scope}")
    private String GOOGLE_DATA_ACCESS_SCOPE;

    private final GoogleAuthClient googleAuthClient;
    private final GoogleApiClient googleApiClient;

    public String getOauthRedirectURL() {
        Map<String, String> params = new HashMap<>();

        params.put(SCOPE, GOOGLE_DATA_ACCESS_SCOPE);
        params.put(RESPONSE_TYPE, RESPONSE_TYPE_CODE);
        params.put(CLIENT_ID, GOOGLE_SNS_CLIENT_ID);
        params.put(REDIRECT_URI, GOOGLE_SNS_CALLBACK_LOGIN_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + KEY_VALUE_DELIMITER + x.getValue())
                .collect(Collectors.joining(QUERY_DELIMITER));

        return GOOGLE_SNS_URL + QUERY_PREFIX + parameterString;
    }

    public GoogleOAuthToken requestAccessToken(String code) {

        return googleAuthClient.getGoogleAccessToken(CONTENT_TYPE, code,
                GOOGLE_SNS_CLIENT_ID, GOOGLE_SNS_CLIENT_SECRET,
                GOOGLE_SNS_CALLBACK_LOGIN_URL, GRANT_TYPE);
    }

    public GoogleUser requestUserInfo(GoogleOAuthToken oAuthToken) {
        return SafeFeignExecutor.run(
                () -> googleApiClient.getGoogleUserInfo(BEARER_PREFIX + oAuthToken.access_token()));
    }
}
