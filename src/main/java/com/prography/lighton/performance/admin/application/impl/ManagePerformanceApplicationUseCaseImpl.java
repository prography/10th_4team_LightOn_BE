package com.prography.lighton.performance.admin.application.impl;

import com.prography.lighton.performance.admin.application.ManagePerformanceApplicationUseCase;
import com.prography.lighton.performance.admin.infrastructure.repository.AdminPerformanceRepository;
import com.prography.lighton.performance.admin.presentation.dto.request.ManagePerformanceApplicationRequestDTO;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagePerformanceApplicationUseCaseImpl implements ManagePerformanceApplicationUseCase {

    private final AdminPerformanceRepository adminPerformanceRepository;
    private final PerformanceRepository performanceRepository;

    @Override
    public void managePerformanceApplication(Long performanceId, ManagePerformanceApplicationRequestDTO requestDTO) {
        Performance performance = performanceRepository.getById(performanceId);
        ApproveStatus approveStatus = ApproveStatus.from(requestDTO.status());
        performance.managePerformanceApplication(approveStatus);
    }
}
