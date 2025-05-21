package com.prography.lighton.member.application.member;

import com.prography.lighton.member.presentation.dto.request.CompleteMemberProfileRequestDTO;
import com.prography.lighton.member.presentation.dto.response.CompleteMemberProfileResponseDTO;

public interface CompleteMemberProfileUseCase {

    CompleteMemberProfileResponseDTO completeMemberProfile(final Long temporaryMemberId,
                                                           final CompleteMemberProfileRequestDTO request);
}
