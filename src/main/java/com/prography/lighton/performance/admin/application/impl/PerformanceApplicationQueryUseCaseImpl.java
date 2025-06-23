package com.prography.lighton.performance.admin.application.impl;

import static com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus.PENDING;
import static com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus.REJECTED;

import com.prography.lighton.performance.admin.application.PerformanceApplicationQueryUseCase;
import com.prography.lighton.performance.admin.application.mapper.PendingPerformanceMapper;
import com.prography.lighton.performance.admin.infrastructure.repository.AdminPerformanceRepository;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationListResponseDTO;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PerformanceApplicationQueryUseCaseImpl implements PerformanceApplicationQueryUseCase {

    private final AdminPerformanceRepository adminPerformanceRepository;
    private final PendingPerformanceMapper pendingPerformanceMapper;

    @Override
    public GetPerformanceApplicationListResponseDTO getAllPerformanceApplications(int page, int size,
                                                                                  List<ApproveStatus> approveStatuses) {
        Pageable pageable = PageRequest.of(page, size);

        List<ApproveStatus> effectiveStatuses;
        if (approveStatuses == null || approveStatuses.isEmpty()) {
            effectiveStatuses = List.of(PENDING, REJECTED); // 기본값
        } else {
            effectiveStatuses = approveStatuses;
        }
        Page<Performance> performances = adminPerformanceRepository.findByApproveStatuses(effectiveStatuses, pageable);
        var dtoPage = performances.map(pendingPerformanceMapper::toPendingPerformanceDTO);
        return GetPerformanceApplicationListResponseDTO.of(dtoPage);
    }

    @Override
    public GetPerformanceApplicationDetailResponseDTO getPendingPerformanceDetail(Long performanceId) {
        Performance performance = adminPerformanceRepository.getById(performanceId);
        performance.validateIsVisibleOnAdminPage();

        return pendingPerformanceMapper.toPendingPerformanceDetailResponseDTO(performance);
    }


}
