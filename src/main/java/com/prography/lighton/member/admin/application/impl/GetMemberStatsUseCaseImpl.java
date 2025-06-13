package com.prography.lighton.member.admin.application.impl;

import com.prography.lighton.member.admin.application.GetMemberStatsUseCase;
import com.prography.lighton.member.admin.infrastructure.AdminMemberRepository;
import com.prography.lighton.member.admin.presentation.dto.response.GetMemberStatsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetMemberStatsUseCaseImpl implements GetMemberStatsUseCase {

    private final AdminMemberRepository adminMemberRepository;

    @Override
    public GetMemberStatsResponseDTO getMemberStats() {
        return GetMemberStatsResponseDTO.of(adminMemberRepository.count());
    }
}
