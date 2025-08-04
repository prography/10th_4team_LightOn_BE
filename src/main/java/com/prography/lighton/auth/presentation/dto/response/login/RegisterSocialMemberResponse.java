package com.prography.lighton.auth.presentation.dto.response.login;

public record RegisterSocialMemberResponse(
        boolean isRegistered, Long temporaryUserId) implements SocialLoginResult {
    public static RegisterSocialMemberResponse of(boolean isRegistered, Long temporaryUserId) {
        return new RegisterSocialMemberResponse(isRegistered, temporaryUserId);
    }
}
