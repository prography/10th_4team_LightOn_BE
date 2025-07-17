package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.performance.users.application.resolver.PerformanceListHelper;
import com.prography.lighton.performance.users.infrastructure.repository.RecentPerformanceRepository;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceBrowseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRecentPerformanceService {

    private static final int LIMIT = 30;
    private static final String RECENT_CACHE_KEY_PREFIX = "recent:";
    private static final String ALL_GENRE_KEY = "all";

    private final PerformanceListHelper helper;
    private final RecentPerformanceRepository recentPerformanceRepository;

    public GetPerformanceBrowseResponse getRecentPerformances(String genreName) {
        String key = buildKey(genreName);
        return helper.fetchWithCache(
                key,
                () -> recentPerformanceRepository.findRecentIds(genreName, LIMIT)
        );
    }

    private String buildKey(String genre) {
        String normalizeGenre = StringUtils.hasText(genre) ? genre.toLowerCase() : ALL_GENRE_KEY;
        return RECENT_CACHE_KEY_PREFIX + normalizeGenre;
    }


}
