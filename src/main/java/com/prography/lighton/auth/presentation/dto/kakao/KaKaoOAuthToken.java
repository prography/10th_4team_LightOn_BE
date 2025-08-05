package com.prography.lighton.auth.presentation.dto.kakao;

public record KaKaoOAuthToken(
        String token_type,
        String access_token,
        String refresh_token,
        String expires_in,
        String refresh_token_expires_in
) {
}
