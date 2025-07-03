package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.performance.users.application.resolver.PerformanceListHelper;
import com.prography.lighton.performance.users.infrastructure.repository.PerformancePopularRepository;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceBrowseResponse;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPopularService {

    private static final int LIMIT = 30;
    private static final String POPULAR_CACHE_KEY_PREFIX = "popular:";
    private static final String ALL_GENRE_KEY = "all";

    private final PerformanceListHelper helper;
    private final PerformancePopularRepository popularRepository;

    public GetPerformanceBrowseResponse getPopular(String genre) {
        String key = buildKey(genre);
        return helper.fetchWithCache(
                key,
                () -> popularRepository.findTopPopularIds(genre, LIMIT));
    }

    private String buildKey(String genre) {
        String normalizeGenre = StringUtils.isBlank(genre) ? ALL_GENRE_KEY : genre.toLowerCase();
        return POPULAR_CACHE_KEY_PREFIX + normalizeGenre;
    }
}
