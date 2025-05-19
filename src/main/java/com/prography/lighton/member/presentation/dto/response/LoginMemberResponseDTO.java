package com.prography.lighton.member.presentation.dto.response;

public record LoginMemberResponseDTO(
        boolean isRegistered,
        String accessToken,
        String refreshToken,
        Long memberId,
        Long temporaryMemberId
) {
    public static LoginMemberResponseDTO registered(String accessToken, String refreshToken, Long memberId) {
        return new LoginMemberResponseDTO(true, accessToken, refreshToken, memberId, null);
    }

    public static LoginMemberResponseDTO temporary(Long temporaryMemberId) {
        return new LoginMemberResponseDTO(false, null, null, null, temporaryMemberId);
    }
}
