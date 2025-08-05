package com.prography.lighton.member.users.presentation.dto.response;

public record RegisterMemberResponse(Long temporaryUserId) {

    public static RegisterMemberResponse of(Long temporaryUserId) {
        return new RegisterMemberResponse(temporaryUserId);
    }
}
