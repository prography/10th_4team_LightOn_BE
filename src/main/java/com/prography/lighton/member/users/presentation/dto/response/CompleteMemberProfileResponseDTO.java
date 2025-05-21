package com.prography.lighton.member.users.presentation.dto.response;

public record CompleteMemberProfileResponseDTO(
        String accessToken,
        String refreshToken
) {

    public static CompleteMemberProfileResponseDTO of(
            String accessToken,
            String refreshToken
    ) {
        return new CompleteMemberProfileResponseDTO(accessToken, refreshToken);
    }
}
