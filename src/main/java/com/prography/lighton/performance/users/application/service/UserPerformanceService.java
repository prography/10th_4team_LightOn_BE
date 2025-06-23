package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRequestRepository;
import com.prography.lighton.performance.users.presentation.dto.response.RequestPerformanceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPerformanceService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceRequestRepository performanceRequestRepository;

    @Transactional
    public RequestPerformanceResponseDTO requestForPerformance(Long performanceId, Long requestedSeats, Member member) {
        Performance performance = performanceRepository.getByIdWithPessimisticLock(performanceId);
        PerformanceRequest performanceRequest = performance.createRequest(requestedSeats, member);
        performanceRequestRepository.save(performanceRequest);

        return RequestPerformanceResponseDTO.of(performance);
    }
}
