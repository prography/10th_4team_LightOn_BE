package com.prography.lighton.auth.application;

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.member.presentation.dto.response.LoginMemberResponseDTO;
import java.io.IOException;

public interface OAuthUseCase {

    String accessRequest(SocialLoginType socialLoginType) throws IOException;

    LoginMemberResponseDTO oAuthLoginOrJoin(SocialLoginType socialLoginType, String code);
}
