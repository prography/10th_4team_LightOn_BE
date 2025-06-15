package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Busking;
import com.prography.lighton.performance.users.application.resolver.PerformanceResolver;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.presentation.dto.BuskingRegisterRequest;
import com.prography.lighton.performance.users.presentation.dto.BuskingUpdateRequest;
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

    @Transactional
    public void registerBusking(Member member, BuskingRegisterRequest request) {
        var data = performanceResolver.toBuskingData(member, request.info(), request.schedule());
        Busking busking = Busking.create(member, data.info(), data.schedule(),
                data.location(), data.genres(), request.proof());
        performanceRepository.save(busking);
    }

    @Transactional
    public void updateBusking(Member member, Long buskingId, BuskingUpdateRequest request) {
        Busking busking = getApprovedBuskingById(buskingId);
        var data = performanceResolver.toBuskingData(member, request.info(), request.schedule());
        busking.update(member, data.info(), data.schedule(), data.location(), data.genres(), request.proof());
    }

    @Transactional
    public void cancelBusking(Member member, Long buskingId) {
        Busking busking = getApprovedBuskingById(buskingId);
        busking.cancel(member);
    }
}
