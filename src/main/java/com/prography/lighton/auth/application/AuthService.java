package com.prography.lighton.auth.application;

import com.prography.lighton.auth.presentation.dto.response.ReissueTokenResponse;
import com.prography.lighton.member.domain.entity.Member;

public interface AuthService {

    ReissueTokenResponse reissueLoginTokens(String refreshToken);

    void logout(Member member);
}
