package com.prography.lighton.member.presentation.dto.response;

public record LoginMemberResponseDTO (String accessToken,
									  String refreshToken,
									  Long memberId) {
	public static LoginMemberResponseDTO of(
			String accessToken,
			String refreshToken,
			Long memberId
	) {
		return new LoginMemberResponseDTO(accessToken, refreshToken, memberId);
	}
}
