package com.prography.lighton.member.application;

import com.prography.lighton.member.presentation.dto.request.CompleteMemberProfileRequestDTO;
import com.prography.lighton.member.presentation.dto.request.EditMemberGenreRequestDTO;
import com.prography.lighton.member.presentation.dto.response.CompleteMemberProfileResponseDTO;

public interface CompleteMemberProfileUseCase {

    CompleteMemberProfileResponseDTO completeMemberProfile(final Long temporaryMemberId,
                                                           final CompleteMemberProfileRequestDTO request);

    void editMemberGenre(final Long memberId, final EditMemberGenreRequestDTO request);
}
