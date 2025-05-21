package com.prography.lighton.auth.presentation.dto.response.login;

public record LoginSocialMemberResponseDTO(
        boolean isRegistered,
        String accessToken,
        String refreshToken) implements SocialLoginResult {


    public static LoginSocialMemberResponseDTO from(
            boolean isRegistered,
            String accessToken,
            String refreshToken) {
        return new LoginSocialMemberResponseDTO(isRegistered, accessToken, refreshToken);
    }
}
