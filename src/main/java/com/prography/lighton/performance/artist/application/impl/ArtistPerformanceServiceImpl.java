package com.prography.lighton.performance.artist.application.impl;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.artist.application.ArtistPerformanceService;
import com.prography.lighton.performance.artist.presentation.dto.response.GetPerformanceRequestsResponseDTO;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceGenre;
import com.prography.lighton.performance.common.domain.entity.enums.RequestStatus;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistPerformanceServiceImpl implements ArtistPerformanceService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceRequestRepository performanceRequestRepository;

    @Override
    public GetPerformanceRequestsResponseDTO getPerformanceRequests(Long performanceId) {
        Performance performance = performanceRepository.getById(performanceId);
        performance.validateApproved();

        return GetPerformanceRequestsResponseDTO.of(
                performance.getId(),
                performance.getInfo(),
                performance.getGenres().stream().map(PerformanceGenre::getGenre).toList(),
                performance.getSchedule(),
                performanceRequestRepository.findAllByPerformance(performance));
    }

    @Override
    public void managePerformanceRequest(Long performanceId, Member member, RequestStatus requestStatus) {
        Performance performance = performanceRepository.getById(performanceId);
        performance.validateApproved();

        PerformanceRequest performanceRequest = performanceRequestRepository.getByMemberAndPerformance(member, performance);
        performanceRequest.updateRequestStatus(requestStatus);

        // TODO 확정 시 유저에게 알림 발송
    }
}
