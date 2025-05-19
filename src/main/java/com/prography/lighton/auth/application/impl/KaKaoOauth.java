package com.prography.lighton.auth.application.impl;

import com.prography.lighton.auth.application.SocialOauth;
import com.prography.lighton.auth.infrastructure.client.kakao.KaKaoApiClient;
import com.prography.lighton.auth.infrastructure.client.kakao.KaKaoAuthClient;
import com.prography.lighton.auth.presentation.dto.kakao.KaKaoOAuthTokenDTO;
import com.prography.lighton.auth.presentation.dto.kakao.KaKaoUser;
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
public class KaKaoOauth implements SocialOauth {

    @Value("${spring.OAuth2.kakao.url}")
    private String KAKAO_SNS_URL;

    @Value("${spring.OAuth2.kakao.client-id}")
    private String KAKAO_SNS_CLIENT_ID;

    @Value("${spring.OAuth2.kakao.callback-login-url}")
    private String KAKAO_SNS_CALLBACK_LOGIN_URL;

    private static final String GRANT_TYPE = "authorization_code";
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    private final KaKaoApiClient kaKaoApiClient;
    private final KaKaoAuthClient kaKaoAuthClient;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", KAKAO_SNS_CLIENT_ID);
        params.put("redirect_uri", KAKAO_SNS_CALLBACK_LOGIN_URL);
        params.put("response_type", "code");
        log.info(KAKAO_SNS_CALLBACK_LOGIN_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        String redirectURL = KAKAO_SNS_URL + "?" + parameterString;
        log.info("redirectURL = {}", redirectURL);
        return redirectURL;
    }

    public KaKaoOAuthTokenDTO requestAccessToken(String code) {
        KaKaoOAuthTokenDTO kaKaoAccessToken = kaKaoAuthClient.getKaKaoAccessToken(
                CONTENT_TYPE, GRANT_TYPE, KAKAO_SNS_CALLBACK_LOGIN_URL, KAKAO_SNS_CLIENT_ID, code);
        log.info(kaKaoAccessToken.toString());
        return kaKaoAccessToken;
    }


    public KaKaoUser requestUserInfo(KaKaoOAuthTokenDTO kaKaoOAuthTokenDTO) {
        KaKaoUser kaKaoUser = kaKaoApiClient.getKaKaoUserInfo(
                getAccessToken(kaKaoOAuthTokenDTO), CONTENT_TYPE);
        log.info(kaKaoUser.toString());
        return kaKaoUser;
    }

    private static String getAccessToken(KaKaoOAuthTokenDTO kaKaoOAuthTokenDTO) {
        return "Bearer " + kaKaoOAuthTokenDTO.access_token();
    }


}