package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.users.application.resolver.PerformanceListHelper;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRecommendationRepository;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceBrowseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRecommendationService {

    private static final int LIMIT = 50;
    private static final String RECOMMENDATION_CACHE_KEY_PREFIX = "recommend:";

    private final PerformanceListHelper helper;
    private final PerformanceRecommendationRepository recommendationRepository;
    private final UserRecentPerformanceService recentPerformanceService;

    public GetPerformanceBrowseResponse getRecommendations(Member member) {
        if (member.getPreferredGenres().isEmpty()) {
            return recentPerformanceService.getRecentPerformances(null);
        }
        String key = buildKey(member);
        return helper.fetchWithCache(
                key,
                () -> recommendationRepository.findTopRecommendedIds(member.getId(), LIMIT));
    }

    public void deleteCache(Member member) {
        String key = buildKey(member);
        helper.deleteCache(key);
    }

    private String buildKey(Member member) {
        return RECOMMENDATION_CACHE_KEY_PREFIX + member.getId();
    }
}
