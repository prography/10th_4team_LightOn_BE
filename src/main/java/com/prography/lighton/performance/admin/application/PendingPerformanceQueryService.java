package com.prography.lighton.performance.admin.application;

import com.prography.lighton.performance.admin.application.mapper.PendingPerformanceMapper;
import com.prography.lighton.performance.admin.infrastructure.repository.AdminPerformanceRepository;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationListResponseDTO;
import com.prography.lighton.performance.users.domain.entity.Performance;
import com.prography.lighton.performance.users.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import java.util.Optional;
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
    public GetPerformanceApplicationListResponseDTO getAllPendingPerformances(int page, int size) {
        return getPerformances(PageRequest.of(page, size), Optional.empty());
    }

    @Override
    public GetPerformanceApplicationListResponseDTO getPendingPerformancesByApproveStatus(int page, int size,
                                                                                          ApproveStatus approveStatus) {
        return getPerformances(PageRequest.of(page, size), Optional.of(approveStatus));
    }

    private GetPerformanceApplicationListResponseDTO getPerformances(Pageable pageable,
                                                                     Optional<ApproveStatus> optionalStatus) {
        Page<Performance> performances = optionalStatus
                .map(status -> adminPerformanceRepository.findByApproveStatus(status, pageable))
                .orElseGet(() -> adminPerformanceRepository.findAllPerformanceApplications(pageable));

        log.info(performances.toString());
        var dtoPage = performances.map(PendingPerformanceMapper::toPendingPerformanceDTO);
        return GetPerformanceApplicationListResponseDTO.of(dtoPage);
    }

    @Override
    public GetPerformanceApplicationDetailResponseDTO getPendingPerformanceDetail(Long performanceId) {
        Performance performance = performanceRepository.getById(performanceId);

        return PendingPerformanceMapper.toPendingPerformanceDetailResponseDTO(performance);
    }
}
