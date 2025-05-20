package com.prography.lighton.auth.presentation.dto.response.login;

public record LoginSocialMemberResponseDTO(
        String accessToken,
        String refreshToken) implements SocialLoginResult {

    public static LoginSocialMemberResponseDTO of(
            String accessToken,
            String refreshToken) {
        return new LoginSocialMemberResponseDTO(accessToken, refreshToken);
    }
}
