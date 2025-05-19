package com.prography.lighton.auth.application;

import com.prography.lighton.auth.domain.enums.SocialLoginType;

public interface OAuthUseCase {

    void accessRequest(SocialLoginType socialLoginType);

    void oAuthLoginOrJoin(SocialLoginType socialLoginType, String code);
}
