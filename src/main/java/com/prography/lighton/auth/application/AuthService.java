package com.prography.lighton.auth.application;

import com.prography.lighton.auth.presentation.dto.request.VerifyPhoneRequestDTO;
import com.prography.lighton.auth.presentation.dto.response.ReissueTokenResponse;
import com.prography.lighton.member.domain.entity.Member;

public interface AuthService {

    ReissueTokenResponse reissueLoginTokens(String refreshToken);

    void logout(Member member);

    void sendAuthCode(String phoneNumber);

    void verifyPhone(VerifyPhoneRequestDTO request);
}
