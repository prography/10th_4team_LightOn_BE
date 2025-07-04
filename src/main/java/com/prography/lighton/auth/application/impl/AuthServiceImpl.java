package com.prography.lighton.auth.application.impl;

import com.prography.lighton.auth.application.AuthService;
import com.prography.lighton.auth.application.AuthVerificationService;
import com.prography.lighton.auth.application.RefreshTokenService;
import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.application.exception.PhoneVerificationFailedException;
import com.prography.lighton.auth.domain.vo.VerificationCode;
import com.prography.lighton.auth.infrastructure.sms.SmsService;
import com.prography.lighton.auth.presentation.dto.request.VerifyPhoneRequestDTO;
import com.prography.lighton.auth.presentation.dto.response.ReissueTokenResponse;
import com.prography.lighton.member.common.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final static String ROLE_KEY = "roles";

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final SmsService smsService;
    private final AuthVerificationService authVerificationService;

    @Override
    public ReissueTokenResponse reissueLoginTokens(String refreshToken) {
        var claims = tokenProvider.getClaims(refreshToken);

        var userId = claims.getSubject();
        var role = claims.get(ROLE_KEY, String.class);

        refreshTokenService.validateRefreshToken(userId, refreshToken);

        String newAccessToken = tokenProvider.createAccessToken(userId, role);
        String newRefreshToken = tokenProvider.createRefreshToken(userId, role);
        refreshTokenService.saveRefreshToken(userId, newRefreshToken);

        return ReissueTokenResponse.of(newAccessToken, newRefreshToken);
    }

    @Override
    public void logout(Member member) {
        refreshTokenService.deleteRefreshToken(member.getId().toString());
    }

    @Override
    public void sendAuthCode(String phoneNumber) {
        VerificationCode code = VerificationCode.generateCode();
        smsService.sendSms(phoneNumber, code.getValue());
        authVerificationService.saveCode(phoneNumber, code.getValue());
    }

    @Override
    public void verifyPhone(VerifyPhoneRequestDTO request) {
        if (!authVerificationService.isCodeMatched(request.phoneNumber(), request.code())) {
            throw new PhoneVerificationFailedException();
        }
        authVerificationService.saveVerifiedStatus(request.phoneNumber());
    }
}
