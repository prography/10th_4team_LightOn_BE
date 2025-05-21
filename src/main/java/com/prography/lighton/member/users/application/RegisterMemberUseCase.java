package com.prography.lighton.member.users.application;

import com.prography.lighton.member.users.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.users.presentation.dto.response.RegisterMemberResponseDTO;

public interface RegisterMemberUseCase {

    RegisterMemberResponseDTO registerMember(final RegisterMemberRequestDTO request);
}
