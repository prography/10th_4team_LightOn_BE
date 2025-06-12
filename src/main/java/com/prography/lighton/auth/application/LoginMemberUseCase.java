package com.prography.lighton.auth.application;

import com.prography.lighton.member.presentation.dto.request.LoginMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.LoginMemberResponseDTO;

public interface LoginMemberUseCase {

    LoginMemberResponseDTO login(final LoginMemberRequestDTO request);
}
