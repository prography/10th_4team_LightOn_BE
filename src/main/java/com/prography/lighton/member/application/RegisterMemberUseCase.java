package com.prography.lighton.member.application;

import com.prography.lighton.member.application.command.RegisterMemberCommand;
import com.prography.lighton.member.presentation.dto.response.SignUpMemberResponseDTO;

public interface RegisterMemberUseCase {

	SignUpMemberResponseDTO registerMember(RegisterMemberCommand command) throws Exception;
}
