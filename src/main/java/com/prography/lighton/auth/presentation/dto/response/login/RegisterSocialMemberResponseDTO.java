package com.prography.lighton.auth.presentation.dto.response.login;

public record RegisterSocialMemberResponseDTO(Long temporaryUserId) implements SocialLoginResult {
    public static RegisterSocialMemberResponseDTO of(Long temporaryUserId) {
        return new RegisterSocialMemberResponseDTO(temporaryUserId);
    }
}
