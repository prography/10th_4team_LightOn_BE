package com.prography.lighton.auth.application.impl;

import com.prography.lighton.auth.application.SocialOauth;
import com.prography.lighton.auth.infrastructure.client.google.GoogleApiClient;
import com.prography.lighton.auth.infrastructure.client.google.GoogleAuthClient;
import com.prography.lighton.auth.presentation.dto.google.GoogleOAuthToken;
import com.prography.lighton.auth.presentation.dto.google.GoogleUser;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleOauth implements SocialOauth {

    @Value("${spring.OAuth2.google.url}")
    private String GOOGLE_SNS_URL;
    @Value("${spring.OAuth2.google.client-id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${spring.OAuth2.google.callback-login-url}")
    private String GOOGLE_SNS_CALLBACK_LOGIN_URL;
    @Value("${spring.OAuth2.google.client-secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${spring.OAuth2.google.scope}")
    private String GOOGLE_DATA_ACCESS_SCOPE;

    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String GRANT_TYPE = "authorization_code";


    private final GoogleAuthClient googleAuthClient;
    private final GoogleApiClient googleApiClient;


    @Override
    public String getOauthRedirectURL() {
        Map<String, String> params = new HashMap<>();

        params.put("scope", GOOGLE_DATA_ACCESS_SCOPE);
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_LOGIN_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));
        String redirectURL = GOOGLE_SNS_URL + "?" + parameterString;
        log.info("redirectURL = ", redirectURL);

        return redirectURL;
    }

    public GoogleOAuthToken requestAccessToken(String code) {
        GoogleOAuthToken googleAccessToken = googleAuthClient.getGoogleAccessToken(CONTENT_TYPE, code,
                GOOGLE_SNS_CLIENT_ID, GOOGLE_SNS_CLIENT_SECRET,
                GOOGLE_SNS_CALLBACK_LOGIN_URL, GRANT_TYPE);

        log.info(googleAccessToken.toString());
        return googleAccessToken;
    }

    public GoogleUser requestUserInfo(GoogleOAuthToken oAuthToken) {
        GoogleUser googleUserInfo = googleApiClient.getGoogleUserInfo("Bearer " + oAuthToken.access_token());

        log.info(googleUserInfo.toString());

        return googleUserInfo;
    }

}
