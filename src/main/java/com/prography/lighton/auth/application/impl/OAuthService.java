package com.prography.lighton.auth.application.impl;

import com.prography.lighton.auth.application.OAuthUseCase;
import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.domain.enums.SocialLoginType;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuthUseCase {

    private final HttpServletResponse response;
    private final TokenProvider tokenProvider;

    public void accessRequest(SocialLoginType socialLoginType) throws IOException {
        String redirectURL;

        switch (socialLoginType){ //각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해주는 프로세스이다.
            case GOOGLE:{
                redirectURL = "googleOauth.getOauthRedirectURL();";
            } break;
            case KAKAO:{
                redirectURL = "kaKaoOauth.getOauthRedirectURL();";
            } break;
            case APPLE: {
                redirectURL = " ";
            } break;
            default:{
                throw new RuntimeException();
            }
        }
        response.sendRedirect(redirectURL);
    }


    public void oAuthLoginOrJoin(SocialLoginType socialLoginType, String code)  {
        switch (socialLoginType) {
            case GOOGLE: {

            } break;
            case KAKAO: {

            } break;
            case APPLE: {

            } break;
            default: {
                throw new RuntimeException();
            }
        }
    }
}
