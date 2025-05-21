package com.prography.lighton.member.application.member;

import com.prography.lighton.member.presentation.dto.request.LoginMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.LoginMemberResponseDTO;

public interface LoginMemberUseCase {

    LoginMemberResponseDTO login(final LoginMemberRequestDTO request);
}
