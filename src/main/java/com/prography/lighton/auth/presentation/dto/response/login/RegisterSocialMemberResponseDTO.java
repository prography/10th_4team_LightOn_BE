package com.prography.lighton.auth.presentation.dto.response.login;

public record RegisterSocialMemberResponseDTO(
        boolean isRegistered, Long temporaryUserId) implements SocialLoginResult {
    public static RegisterSocialMemberResponseDTO of(boolean isRegistered, Long temporaryUserId) {
        return new RegisterSocialMemberResponseDTO(isRegistered, temporaryUserId);
    }
}
