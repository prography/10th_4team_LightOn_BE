package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.artist.users.application.service.ArtistLikeRedisService;
import com.prography.lighton.artist.users.application.service.ArtistLikeRedisService.ArtistRankDto;
import com.prography.lighton.performance.users.application.resolver.PerformanceListHelper;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceLatestByArtistRepository;
import com.prography.lighton.performance.users.infrastructure.repository.RecentPerformanceRepository;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceBrowseResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserHotArtistPerformanceService {

    private static final String LATEST_HOT_CACHE_KEY_PREFIX = "latest_hot";
    private static final int TOP_ARTIST_COUNT = 10;
    private static final int HOT_PERFORMANCE_COUNT = 5;

    private final PerformanceLatestByArtistRepository latestRepository;
    private final ArtistLikeRedisService artistLikeRedisService;
    private final PerformanceListHelper helper;
    private final RecentPerformanceRepository recentRepository;

    public GetPerformanceBrowseResponse getLatestHotArtistPerformance() {
        String key = LATEST_HOT_CACHE_KEY_PREFIX;
        return helper.fetchWithCache(
                key, this::findLatestHotPerformanceIds);
    }

    private List<Long> findLatestHotPerformanceIds() {
        List<Long> topArtists = artistLikeRedisService
                .topArtistsLast14Days(TOP_ARTIST_COUNT)
                .stream()
                .map(ArtistRankDto::artistId)
                .toList();

        return buildHotPerformances(topArtists);
    }

    private List<Long> buildHotPerformances(List<Long> artistIds) {
        if (artistIds.isEmpty()) {
            return recentRepository.findRecentAll(HOT_PERFORMANCE_COUNT);
        }

        List<Long> hotIds = artistIds.stream()
                .map(latestRepository::findLatestUpcomingIdByArtist)
                .flatMap(Optional::stream)
                .distinct()
                .limit(HOT_PERFORMANCE_COUNT)
                .toList();

        if (hotIds.size() < HOT_PERFORMANCE_COUNT) {
            int needed = HOT_PERFORMANCE_COUNT - hotIds.size();
            List<Long> fallback = recentRepository
                    .findRecentExcluding(hotIds, needed);
            hotIds = Stream.concat(hotIds.stream(), fallback.stream())
                    .limit(HOT_PERFORMANCE_COUNT)
                    .toList();
        }

        return hotIds;
    }
}
