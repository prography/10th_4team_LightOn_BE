package com.prography.lighton.member.application.member;

import com.prography.lighton.member.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.RegisterMemberResponseDTO;

public interface RegisterMemberUseCase {

    RegisterMemberResponseDTO registerMember(final RegisterMemberRequestDTO request);
}
