package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.users.application.service.ArtistService;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.users.application.resolver.PerformanceResolver;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.presentation.dto.PerformanceRegisterRequest;
import com.prography.lighton.performance.users.presentation.dto.PerformanceUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceResolver performanceResolver;
    private final ArtistService artistService;

    public Performance getApprovedPerformanceById(Long id) {
        Performance performance = performanceRepository.getById(id);
        performance.validateApproved();
        return performance;
    }

    @Transactional
    public void registerPerformance(Member member, PerformanceRegisterRequest request) {
        var data = performanceResolver.toDomainData(member, request.artists(), request.info(), request.schedule(),
                request.payment(),
                request.seat());
        Performance performance = Performance.create(data.master(), data.artists(), data.info(), data.schedule(),
                data.location(),
                data.payment(),
                Type.NORMAL, data.seats(), data.genres(), request.proof());
        performanceRepository.save(performance);
    }

    @Transactional
    public void updatePerformance(Member member, Long performanceId, PerformanceUpdateRequest request) {
        Performance performance = getApprovedPerformanceById(performanceId);
        var data = performanceResolver.toDomainData(member, request.artists(), request.info(), request.schedule(),
                request.payment(),
                request.seat());
        performance.update(data.master(), data.artists(), data.info(), data.schedule(), data.location(), data.payment(),
                data.seats(),
                data.genres(), request.proof());
    }

    @Transactional
    public void cancelPerformance(Member member, Long performanceId) {
        // 여기 나중에 책임분리 하기
        Artist requestArtist = artistService.getApprovedArtistByMember(member);
        Performance performance = getApprovedPerformanceById(performanceId);
        performance.cancel(requestArtist);
    }
}
