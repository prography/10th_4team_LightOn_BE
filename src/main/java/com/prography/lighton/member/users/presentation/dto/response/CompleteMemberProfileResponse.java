package com.prography.lighton.member.users.presentation.dto.response;

public record CompleteMemberProfileResponse(
        String accessToken,
        String refreshToken
) {

    public static CompleteMemberProfileResponse of(
            String accessToken,
            String refreshToken
    ) {
        return new CompleteMemberProfileResponse(accessToken, refreshToken);
    }
}
