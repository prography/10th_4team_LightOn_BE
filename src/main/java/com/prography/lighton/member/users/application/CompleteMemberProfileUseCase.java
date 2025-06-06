package com.prography.lighton.member.users.application;

import com.prography.lighton.member.users.presentation.dto.request.CompleteMemberProfileRequestDTO;
import com.prography.lighton.member.users.presentation.dto.response.CompleteMemberProfileResponseDTO;

public interface CompleteMemberProfileUseCase {

    CompleteMemberProfileResponseDTO completeMemberProfile(final Long temporaryMemberId,
                                                           final CompleteMemberProfileRequestDTO request);
}
