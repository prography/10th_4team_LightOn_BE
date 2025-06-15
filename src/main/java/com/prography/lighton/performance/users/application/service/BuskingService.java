package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.performance.common.domain.entity.Busking;
import com.prography.lighton.performance.users.application.resolver.PerformanceResolver;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuskingService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceResolver performanceResolver;

    public Busking getApprovedBuskingById(Long id) {
        Busking performance = performanceRepository.getByBuskingId(id);
        performance.validateApproved();
        return performance;
    }

}
