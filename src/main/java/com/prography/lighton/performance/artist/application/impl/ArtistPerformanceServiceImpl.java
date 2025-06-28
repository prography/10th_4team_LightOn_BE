package com.prography.lighton.performance.artist.application.impl;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.artist.application.ArtistPerformanceService;
import com.prography.lighton.performance.artist.presentation.dto.response.GetPerformanceRequestsResponseDTO;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceGenre;
import com.prography.lighton.performance.common.domain.entity.enums.RequestStatus;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRequestRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistPerformanceServiceImpl implements ArtistPerformanceService {

    private final GenreCache genreCache;

    private final PerformanceRepository performanceRepository;
    private final PerformanceRequestRepository performanceRequestRepository;

    @Override
    public GetPerformanceRequestsResponseDTO getPerformanceRequests(Long performanceId, Member member) {
        Performance performance = performanceRepository.getById(performanceId);
        performance.validateIsManagedBy(member);

        return GetPerformanceRequestsResponseDTO.of(
                performance.getId(),
                performance.getInfo(),
                toGenres(performance.getGenres()),
                performance.getSchedule(),
                performanceRequestRepository.findAllByPerformance(performance));
    }

    @Override
    @Transactional
    public void managePerformanceRequest(Long performanceId, Member member, RequestStatus requestStatus) {
        Performance performance = performanceRepository.getById(performanceId);
        performance.validateIsManagedBy(member);

        PerformanceRequest performanceRequest = performanceRequestRepository.getByMemberAndPerformance(member, performance);
        performanceRequest.updateRequestStatus(requestStatus);

        // TODO 확정 시 유저에게 알림 발송
    }

    private List<String> toGenres(List<PerformanceGenre> performanceGenres) {
        List<Long> genreIds = performanceGenres.stream()
                .map(ag -> ag.getGenre().getId())
                .toList();

        return genreCache.getGenresOrThrow(genreIds).stream()
                .map(Genre::getName)
                .toList();
    }
}
