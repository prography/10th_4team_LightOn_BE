package com.prography.lighton.member.users.presentation.dto.response;

public record LoginMemberResponseDTO(String accessToken,
                                     String refreshToken) {
    public static LoginMemberResponseDTO of(
            String accessToken,
            String refreshToken
    ) {
        return new LoginMemberResponseDTO(accessToken, refreshToken);
    }
}
