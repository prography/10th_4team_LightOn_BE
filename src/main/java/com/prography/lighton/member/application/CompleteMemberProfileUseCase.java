package com.prography.lighton.member.application;

import com.prography.lighton.member.presentation.dto.request.CompleteMemberProfileRequestDTO;

public interface CompleteMemberProfileUseCase {

	void completeMemberProfile(final Long memberId, final CompleteMemberProfileRequestDTO request);
}
