package com.prography.lighton.auth.application;


import com.prography.lighton.member.users.presentation.dto.request.LoginMemberRequest;
import com.prography.lighton.member.users.presentation.dto.response.LoginMemberResponse;

public interface LoginMemberUseCase {

    LoginMemberResponse login(final LoginMemberRequest request);
}
