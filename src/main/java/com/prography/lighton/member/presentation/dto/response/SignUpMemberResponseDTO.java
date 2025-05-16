package com.prography.lighton.member.presentation.dto.response;

public record SignUpMemberResponseDTO (String accessToken, String refreshToken, Long userId) {

	public static SignUpMemberResponseDTO of(String accessToken, String refreshToken, Long userId) {
		return new SignUpMemberResponseDTO(accessToken, refreshToken, userId);
	}
}
