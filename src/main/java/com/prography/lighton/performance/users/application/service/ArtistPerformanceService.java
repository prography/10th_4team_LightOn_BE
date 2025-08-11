package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.common.geo.BoundingBox;
import com.prography.lighton.common.geo.GeoUtils;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.common.domain.entity.enums.PerformanceFilterType;
import com.prography.lighton.performance.users.application.resolver.PerformanceResolver;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceArtistRepository;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.presentation.dto.request.RegisterPerformanceMultiPart;
import com.prography.lighton.performance.users.presentation.dto.request.UpdatePerformanceMultiPart;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceMapListResponseDTO;
import com.prography.lighton.performance.users.presentation.dto.response.PerformanceSearchItemDTO;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistPerformanceService {

    private static final Integer DAY_OF_WEEK = 7;
    private static final Integer CLOSING_SOON_DAYS = 1;

    private final PerformanceRepository performanceRepository;
    private final PerformanceArtistRepository performanceArtistRepository;
    private final PerformanceResolver performanceResolver;

    public Performance getApprovedPerformanceById(Long id) {
        Performance performance = performanceRepository.getById(id);
        performance.validateApproved();
        return performance;
    }

    public Page<PerformanceSearchItemDTO> searchByKeyword(String keyword, Pageable pageable) {
        return performanceRepository.searchByKeyword(keyword, pageable)
                .map(PerformanceSearchItemDTO::of);
    }

    @Transactional
    public void registerPerformance(Member member, RegisterPerformanceMultiPart request) {
        var data = performanceResolver.toNewPerformanceData(member, request);
        Performance performance = Performance.create(member, data.artists(), data.info(), data.schedule(),
                data.location(),
                data.payment(), data.seats(), data.genres(), data.proofUrl(), request.data().totalSeatsCount());
        performanceRepository.save(performance);
    }

    @Transactional
    public void updatePerformance(Member member, Long performanceId, UpdatePerformanceMultiPart request) {
        Performance performance = getApprovedPerformanceById(performanceId);
        var data = performanceResolver.toUpdatePerformanceData(member, performance, request);
        performance.update(member, data.artists(), data.info(), data.schedule(), data.location(), data.payment(),
                data.seats(),
                data.genres(), data.proofUrl(), request.data().totalSeatsCount());
    }

    @Transactional
    public void cancelPerformance(Member member, Long performanceId) {
        Performance performance = getApprovedPerformanceById(performanceId);
        performance.cancel(member);
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

    public List<Artist> getArtistsByAppliedPerformanceIds(List<Long> performanceIds) {
        return performanceArtistRepository.findAllByPerformances(performanceIds).stream()
                .map(PerformanceArtist::getArtist)
                .distinct()
                .toList();
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
