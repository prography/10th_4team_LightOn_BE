package com.prography.lighton.auth.application;

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.auth.presentation.dto.response.login.SocialLoginResult;
import java.io.IOException;

public interface OAuthUseCase {

    String accessRequest(SocialLoginType socialLoginType) throws IOException;

    SocialLoginResult oAuthLoginOrJoin(SocialLoginType socialLoginType, String code);
}
