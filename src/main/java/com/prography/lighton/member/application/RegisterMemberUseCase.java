package com.prography.lighton.member.application;

import com.prography.lighton.member.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.CheckDuplicateEmailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.RegisterMemberResponseDTO;

public interface RegisterMemberUseCase {

    RegisterMemberResponseDTO registerMember(final RegisterMemberRequestDTO request);

    CheckDuplicateEmailResponseDTO checkEmailExists(final String email);
}
