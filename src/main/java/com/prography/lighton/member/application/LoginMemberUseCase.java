package com.prography.lighton.member.application;

import com.prography.lighton.member.presentation.dto.request.LoginMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.LoginMemberResponseDTO;

public interface LoginMemberUseCase {

	LoginMemberResponseDTO loginMember(final LoginMemberRequestDTO request);
}
