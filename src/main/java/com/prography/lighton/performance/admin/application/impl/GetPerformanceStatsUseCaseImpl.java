package com.prography.lighton.performance.admin.application.impl;

import com.prography.lighton.performance.admin.application.GetPerformanceStatsUseCase;
import com.prography.lighton.performance.admin.infrastructure.repository.AdminPerformanceRepository;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceStatsResponseDTO;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetPerformanceStatsUseCaseImpl implements GetPerformanceStatsUseCase {

    private final AdminPerformanceRepository adminPerformanceRepository;

    @Override
    public GetPerformanceStatsResponseDTO getPerformanceStats() {
        return GetPerformanceStatsResponseDTO.of(
                adminPerformanceRepository.countEndedByApproveStatusAndStatus(ApproveStatus.APPROVED, true),
                adminPerformanceRepository.countByApproveStatusAndStatus(ApproveStatus.APPROVED, true)
        );
    }
}
