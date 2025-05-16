package com.prography.lighton.member.presentation.dto.response;

public record SignUpMemberResponseDTO (Long temporaryUserId) {

	public static SignUpMemberResponseDTO of(Long temporaryUserId) {
		return new SignUpMemberResponseDTO(temporaryUserId);
	}
}
