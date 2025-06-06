package com.prography.lighton.member.users.application;

import com.prography.lighton.member.users.presentation.dto.request.LoginMemberRequestDTO;
import com.prography.lighton.member.users.presentation.dto.response.LoginMemberResponseDTO;

public interface LoginMemberUseCase {

    LoginMemberResponseDTO login(final LoginMemberRequestDTO request);
}
