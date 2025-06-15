package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.users.application.service.ArtistService;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.common.geo.BoundingBox;
import com.prography.lighton.common.geo.GeoUtils;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.PerformanceFilterType;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.users.application.resolver.PerformanceResolver;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.presentation.dto.PerformanceRegisterRequest;
import com.prography.lighton.performance.users.presentation.dto.PerformanceUpdateRequest;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceMapListResponseDTO;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PerformanceService {

    private static final Integer DAY_OF_WEEK = 7;
    private static final Integer CLOSING_SOON_DAYS = 1;


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
                Type.CONCERT, data.seats(), data.genres(), request.proof());
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

    public GetPerformanceMapListResponseDTO findNearbyPerformances(double latitude, double longitude, int radius) {
        BoundingBox box = GeoUtils.getBoundingBox(latitude, longitude, radius);

        List<Performance> performances = performanceRepository.findRoughlyWithinBox(
                box.minLatitude(), box.maxLatitude(), box.minLongitude(), box.maxLongitude()
        );

        return GetPerformanceMapListResponseDTO.from(performances);
    }

    public GetPerformanceMapListResponseDTO findFilteredPerformances(PerformanceFilterType type, double latitude,
                                                                     double longitude, int radius, Member member) {
        LocalDate today = LocalDate.now();
        BoundingBox box = GeoUtils.getBoundingBox(latitude, longitude, radius);

        List<Performance> performances = findPerformancesByType(type, today, box);
        return GetPerformanceMapListResponseDTO.from(performances);
    }

    private List<Performance> findPerformancesByType(PerformanceFilterType type, LocalDate today, BoundingBox box) {
        return switch (type) {
            case RECOMMENDED -> performanceRepository.findRandomRecommendedWithinBox(
                    box.minLatitude(), box.maxLatitude(), box.minLongitude(), box.maxLongitude()
            );
            case RECENT -> performanceRepository.findRegisteredInLastWeekWithinBox(
                    box.minLatitude(), box.maxLatitude(), box.minLongitude(), box.maxLongitude(),
                    today.minusDays(DAY_OF_WEEK).atStartOfDay()
            );
            case CLOSING_SOON -> performanceRepository.findClosingSoonWithinBox(
                    box.minLatitude(), box.maxLatitude(), box.minLongitude(), box.maxLongitude(),
                    today.plusDays(CLOSING_SOON_DAYS)
            );
            default -> List.of();
        };
    }
}
