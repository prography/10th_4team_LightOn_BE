package com.prography.lighton.auth.application.service;

import com.prography.lighton.auth.application.port.RefreshTokenService;
import com.prography.lighton.auth.application.port.TokenProvider;
import com.prography.lighton.auth.presentation.dto.response.ReissueTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenReissueService {

    private final static String ROLE_KEY = "roles";

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

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

    @Transactional
    public void logout(Long memberId) {
        refreshTokenService.deleteRefreshToken(memberId.toString());
    }
}
