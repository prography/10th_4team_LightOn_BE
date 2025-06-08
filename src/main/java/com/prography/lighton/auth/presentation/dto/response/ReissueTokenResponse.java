package com.prography.lighton.auth.presentation.dto.response;

public record ReissueTokenResponse(
        String accessToken,
        String refreshToken
) {

    public static ReissueTokenResponse of(String accessToken, String refreshToken) {
        return new ReissueTokenResponse(accessToken, refreshToken);
    }
}
