package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import com.prography.lighton.performance.common.domain.exception.DuplicatePerformanceRequestException;
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
    public RequestPerformanceResponseDTO requestForPerformance(Long performanceId, Integer applySeats,
                                                               Member member) {
        Performance performance = performanceRepository.getByIdWithPessimisticLock(performanceId);
        performance.validateApproved();

        if (performanceRequestRepository.existsByMemberAndPerformance(member, performance)) {
            throw new DuplicatePerformanceRequestException();
        }

        PerformanceRequest performanceRequest = performance.createRequest(applySeats, member);
        performanceRequestRepository.save(performanceRequest);

        return RequestPerformanceResponseDTO.of(performance, applySeats);
    }

    @Transactional
    public void cancelPerformanceRequest(Long performanceId, Member member) {
        Performance performance = performanceRepository.getByIdWithPessimisticLock(performanceId);
        performance.validateApproved();

        PerformanceRequest performanceRequest = performanceRequestRepository.getByMemberAndPerformance(member,
                performance);

        performance.cancelRequest(performanceRequest.getRequestedSeats());
        performanceRequestRepository.delete(performanceRequest);
    }
}
