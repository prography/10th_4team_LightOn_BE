package com.prography.lighton.member.application.command;

import com.prography.lighton.member.presentation.dto.request.SignUpMemberRequestDTO;

public record RegisterMemberCommand(String email, String password) {

	public static RegisterMemberCommand of(SignUpMemberRequestDTO request) {
		return new RegisterMemberCommand(request.email(), request.password());
	}
}
