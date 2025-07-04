package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.performance.users.application.resolver.PerformanceListHelper;
import com.prography.lighton.performance.users.infrastructure.repository.PerformancePopularRepository;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceBrowseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        String normalizeGenre = StringUtils.hasText(genre) ? genre.toLowerCase() : ALL_GENRE_KEY;
        return POPULAR_CACHE_KEY_PREFIX + normalizeGenre;
    }
}
