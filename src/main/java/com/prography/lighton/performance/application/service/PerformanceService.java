package com.prography.lighton.performance.application.service;

import com.prography.lighton.artist.application.service.ArtistService;
import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.performance.application.resolver.PerformanceResolver;
import com.prography.lighton.performance.domain.entity.Performance;
import com.prography.lighton.performance.domain.entity.enums.Type;
import com.prography.lighton.performance.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.presentation.dto.PerformanceRegisterRequest;
import com.prography.lighton.performance.presentation.dto.PerformanceUpdateRequest;
import jakarta.transaction.Transactional;
import java.util.List;
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
        Artist artist = artistService.getApprovedArtistByMember(member);
        var data = performanceResolver.toDomainData(request.infoDTO(), request.scheduleDTO(), request.paymentDTO(),
                request.seat());
        Performance performance = Performance.create(artist, data.info(), data.schedule(), data.location(),
                data.payment(),
                Type.NORMAL, data.seats(), data.genres(), request.proof());
        performanceRepository.save(performance);
    }

    @Transactional
    public void updatePerformance(Member member, Long performanceId, PerformanceUpdateRequest request) {
        Artist requestArtist = artistService.getApprovedArtistByMember(member);
        Performance performance = getApprovedPerformanceById(performanceId);
        var data = performanceResolver.toDomainData(request.infoDTO(), request.scheduleDTO(), request.paymentDTO(),
                request.seat());
        List<Artist> artists = artistService.getApprovedArtistsByIds(request.artists());
        performance.update(requestArtist, artists, data.info(), data.schedule(), data.location(), data.payment(),
                data.seats(),
                data.genres());
    }
}
