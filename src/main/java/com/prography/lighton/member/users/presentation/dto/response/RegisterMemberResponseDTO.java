package com.prography.lighton.member.users.presentation.dto.response;

public record RegisterMemberResponseDTO(Long temporaryUserId) {

    public static RegisterMemberResponseDTO of(Long temporaryUserId) {
        return new RegisterMemberResponseDTO(temporaryUserId);
    }
}
