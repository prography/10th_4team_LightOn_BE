package com.prography.lighton.performance.admin.application;

import com.prography.lighton.performance.admin.infrastructure.repository.AdminPerformanceRepository;
import com.prography.lighton.performance.admin.presentation.dto.request.ManagePerformanceApplicationRequestDTO;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.exception.NoSuchPerformanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagePerformanceApplicationService implements ManagePerformanceApplicationUseCase {

    private final AdminPerformanceRepository adminPerformanceRepository;

    @Override
    public void manageArtistApplication(Long performanceId, ManagePerformanceApplicationRequestDTO requestDTO) {
        Performance performance = adminPerformanceRepository.findByIdAndApproveStatus(performanceId,
                        ApproveStatus.PENDING)
                .orElseThrow(() -> new NoSuchPerformanceException("해당 공연은 이미 처리 되었거나 존재하지 않습니다."));

        ApproveStatus approveStatus = ApproveStatus.from(requestDTO.status());
        performance.managePerformanceApplication(approveStatus);
    }
}
