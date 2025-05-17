package com.prography.lighton.member.application;

import com.prography.lighton.member.presentation.dto.request.CompleteMemberProfileRequestDTO;

public interface CompleteMemberProfileUseCase {

	void completeMemberProfile(final Long temporaryMemberId, final CompleteMemberProfileRequestDTO request);
}
