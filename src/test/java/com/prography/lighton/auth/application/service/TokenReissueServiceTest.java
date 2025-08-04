package com.prography.lighton.auth.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.prography.lighton.auth.application.fake.FakeRefreshTokenService;
import com.prography.lighton.auth.application.fake.FakeTokenProvider;
import com.prography.lighton.auth.presentation.dto.response.ReissueTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TokenReissueServiceTest {

    private FakeTokenProvider tokenProvider;
    private FakeRefreshTokenService refreshTokenService;
    private TokenReissueService tokenReissueService;

    @BeforeEach
    void setUp() {
        tokenProvider = new FakeTokenProvider();
        refreshTokenService = new FakeRefreshTokenService();
        tokenReissueService = new TokenReissueService(tokenProvider, refreshTokenService);
    }

    @Test
    @DisplayName("토큰 재발급 서비스가 정상적으로 작동한다.")
    void should_reissue_token_successfully() {
        // given
        String userId = "123";
        String refreshToken = tokenProvider.createRefreshToken(userId, "ROLE_USER");

        // Fake에 저장된 refreshToken 세팅
        refreshTokenService.saveRefreshToken(userId, refreshToken);

        // when
        ReissueTokenResponse result = tokenReissueService.reissueLoginTokens(refreshToken);

        // then
        assertEquals("fake-access-token-123", result.accessToken());
        assertEquals("fake-refresh-token-123", result.refreshToken());
    }

    @Test
    @DisplayName("로그아웃 시 리프레시 토큰이 삭제된다.")
    void should_logout_and_delete_refresh_token() {
        // given
        Long userId = 123L;
        String refreshToken = tokenProvider.createRefreshToken(userId.toString(), "ROLE_USER");

        // Fake에 저장된 refreshToken 세팅
        refreshTokenService.saveRefreshToken(userId.toString(), refreshToken);

        // when
        tokenReissueService.logout(userId);

        // then
        assertNull(refreshTokenService.getRefreshToken(userId.toString()));
    }
}
