package com.prography.lighton.auth.application.impl;

import com.prography.lighton.auth.application.AuthService;
import com.prography.lighton.auth.application.RefreshTokenService;
import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.presentation.dto.response.ReissueTokenResponse;
import com.prography.lighton.member.domain.entity.Member;
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

    @Override
    public ReissueTokenResponse reissueLoginTokens(String refreshToken) {
        var claims = tokenProvider.getClaims(refreshToken);

        var userId = claims.getSubject();
        var role = claims.get(ROLE_KEY, String.class);

        refreshTokenService.validate(userId, refreshToken);

        String newAccessToken = tokenProvider.createAccessToken(userId, role);
        String newRefreshToken = tokenProvider.createRefreshToken(userId, role);
        refreshTokenService.save(userId, newRefreshToken);

        return ReissueTokenResponse.of(newAccessToken, newRefreshToken);
    }

    @Override
    public void logout(Member member) {
        refreshTokenService.delete(member.getId().toString());
    }
}
