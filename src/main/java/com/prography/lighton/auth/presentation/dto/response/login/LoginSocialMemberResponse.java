package com.prography.lighton.auth.presentation.dto.response.login;

public record LoginSocialMemberResponse(
        boolean isRegistered,
        String accessToken,
        String refreshToken) implements SocialLoginResult {


    public static LoginSocialMemberResponse from(
            boolean isRegistered,
            String accessToken,
            String refreshToken) {
        return new LoginSocialMemberResponse(isRegistered, accessToken, refreshToken);
    }
}
