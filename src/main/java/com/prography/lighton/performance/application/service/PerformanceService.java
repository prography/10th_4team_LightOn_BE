package com.prography.lighton.performance.application.service;

import com.prography.lighton.artist.application.service.ArtistService;
import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.performance.application.resolver.PerformanceResolver;
import com.prography.lighton.performance.domain.entity.Performance;
import com.prography.lighton.performance.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.presentation.dto.PerformanceRegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceResolver performanceResolver;
    private final ArtistService artistService;

    @Transactional
    public void registerPerformance(Member member, PerformanceRegisterRequest request) {
        Artist artist = artistService.getApprovedArtistByMember(member);
        Performance performance = performanceResolver.resolve(artist, request);
        performanceRepository.save(performance);
    }
}
