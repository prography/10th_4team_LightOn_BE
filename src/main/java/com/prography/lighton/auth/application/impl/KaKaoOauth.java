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

import com.prography.lighton.auth.infrastructure.client.SafeFeignExecutor;
import com.prography.lighton.auth.infrastructure.client.kakao.KaKaoApiClient;
import com.prography.lighton.auth.infrastructure.client.kakao.KaKaoAuthClient;
import com.prography.lighton.auth.presentation.dto.kakao.KaKaoOAuthTokenDTO;
import com.prography.lighton.auth.presentation.dto.kakao.KaKaoUser;
import com.prography.lighton.common.constant.JwtConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KaKaoOauth {

    @Value("${spring.oauth2.kakao.url}")
    private String KAKAO_SNS_URL;

    @Value("${spring.oauth2.kakao.client-id}")
    private String KAKAO_SNS_CLIENT_ID;

    @Value("${spring.oauth2.kakao.callback-login-url}")
    private String KAKAO_SNS_CALLBACK_LOGIN_URL;

    private final KaKaoApiClient kaKaoApiClient;
    private final KaKaoAuthClient kaKaoAuthClient;

    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put(CLIENT_ID, KAKAO_SNS_CLIENT_ID);
        params.put(REDIRECT_URI, KAKAO_SNS_CALLBACK_LOGIN_URL);
        params.put(RESPONSE_TYPE, RESPONSE_TYPE_CODE);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + KEY_VALUE_DELIMITER + x.getValue())
                .collect(Collectors.joining(QUERY_DELIMITER));

        return KAKAO_SNS_URL + QUERY_PREFIX + parameterString;
    }

    public KaKaoOAuthTokenDTO requestAccessToken(String code) {
        return SafeFeignExecutor.execute(
                () -> kaKaoAuthClient.getKaKaoAccessToken(
                        KAKAO_SNS_CLIENT_ID, code, KAKAO_SNS_CALLBACK_LOGIN_URL,
                        GRANT_TYPE, CONTENT_TYPE),
                "카카오 액세스 토큰 요청에 실패했습니다."
        );
    }


    public KaKaoUser requestUserInfo(KaKaoOAuthTokenDTO kaKaoOAuthTokenDTO) {
        return SafeFeignExecutor.execute(
                () -> kaKaoApiClient.getKaKaoUserInfo(
                        getAccessToken(kaKaoOAuthTokenDTO), CONTENT_TYPE),
                "카카오 사용자 정보 요청에 실패했습니다."
        );
    }

    private static String getAccessToken(KaKaoOAuthTokenDTO kaKaoOAuthTokenDTO) {
        return JwtConstants.BEARER_PREFIX + kaKaoOAuthTokenDTO.access_token();
    }


}
