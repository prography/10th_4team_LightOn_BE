package com.prography.lighton.member.presentation.dto.response;

public record CompleteMemberProfileResponseDTO(
		String accessToken,
		String refreshToken,
		Long memberId
) {

	public static CompleteMemberProfileResponseDTO of(
			String accessToken,
			String refreshToken,
			Long memberId
	) {
		return new CompleteMemberProfileResponseDTO(accessToken, refreshToken, memberId);
	}
}