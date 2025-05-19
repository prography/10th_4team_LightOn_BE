package com.prography.lighton.auth.application;

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import java.io.IOException;

public interface OAuthUseCase {

    void accessRequest(SocialLoginType socialLoginType) throws IOException;

    void oAuthLoginOrJoin(SocialLoginType socialLoginType, String code);
}
