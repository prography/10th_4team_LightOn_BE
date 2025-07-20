package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.artist.users.application.service.ArtistLikeRedisService;
import com.prography.lighton.artist.users.application.service.ArtistLikeRedisService.ArtistRankDto;
import com.prography.lighton.performance.users.application.resolver.PerformanceListHelper;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceLatestByArtistRepository;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceBrowseResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserHotArtistPerformanceService {

    private static final String LATEST_HOT_CACHE_KEY_PREFIX = "latest_hot";

    private final PerformanceLatestByArtistRepository latestRepository;
    private final ArtistLikeRedisService artistLikeRedisService;
    private final PerformanceListHelper helper;
    private final UserRecentPerformanceService recentPerformanceService;

    public GetPerformanceBrowseResponse getLatestHotArtistPerformance() {
        String key = LATEST_HOT_CACHE_KEY_PREFIX;
        GetPerformanceBrowseResponse response = helper.fetchWithCache(
                key,
                () -> findLatestHotPerformanceIds());
        if (response.performances().isEmpty()) {
            response = recentPerformanceService.getRecentPerformances("");
        }
        return response;
    }

    private List<Long> findLatestHotPerformanceIds() {

        List<Long> popularArtistIds = artistLikeRedisService.topArtistsLast14Days(30)
                .stream().map(ArtistRankDto::artistId).toList();

        if (popularArtistIds.isEmpty()) {
            return List.of();
        }

        List<Long> ids = latestRepository.findLatestUpcomingIdsByArtists(
                popularArtistIds, LocalDate.now());

        // 아티스트 인기 순서를 유지하며 5개 선택해야함, 지금은 해당 로직 없음.
        //List<Long> ordered = ids.subList(0, 5);

        if (ids.size() < 5) {

        }

        return ids;
    }

}
