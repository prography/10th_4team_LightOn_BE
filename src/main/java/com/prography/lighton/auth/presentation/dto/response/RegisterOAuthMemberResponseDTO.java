package com.prography.lighton.auth.presentation.dto.response;

public record RegisterOAuthMemberResponseDTO(Long temporaryUserId) {

    public static RegisterOAuthMemberResponseDTO of(Long temporaryUserId) {
        return new RegisterOAuthMemberResponseDTO(temporaryUserId);
    }
}