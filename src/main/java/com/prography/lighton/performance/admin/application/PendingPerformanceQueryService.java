package com.prography.lighton.performance.admin.application;

import com.prography.lighton.performance.admin.application.mapper.PendingPerformanceMapper;
import com.prography.lighton.performance.admin.infrastructure.repository.AdminPerformanceRepository;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationListResponseDTO;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
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
public class PendingPerformanceQueryService implements PendingPerformanceQueryUseCase {

    private final PerformanceRepository performanceRepository;
    private final AdminPerformanceRepository adminPerformanceRepository;


    @Override
    public GetPerformanceApplicationListResponseDTO getAllPerformanceApplications(int page, int size,
                                                                                  List<ApproveStatus> approveStatuses) {
        Pageable pageable = PageRequest.of(page, size);

        List<ApproveStatus> effectiveStatuses;
        if (approveStatuses == null || approveStatuses.isEmpty()) {
            effectiveStatuses = List.of(ApproveStatus.PENDING, ApproveStatus.REJECTED); // 기본값
        } else {
            effectiveStatuses = approveStatuses;
        }
        Page<Performance> performances = adminPerformanceRepository.findByApproveStatuses(effectiveStatuses, pageable);
        var dtoPage = performances.map(PendingPerformanceMapper::toPendingPerformanceDTO);
        return GetPerformanceApplicationListResponseDTO.of(dtoPage);
    }

    @Override
    public GetPerformanceApplicationDetailResponseDTO getPendingPerformanceDetail(Long performanceId) {
        Performance performance = performanceRepository.getById(performanceId);

        return PendingPerformanceMapper.toPendingPerformanceDetailResponseDTO(performance);
    }
}
