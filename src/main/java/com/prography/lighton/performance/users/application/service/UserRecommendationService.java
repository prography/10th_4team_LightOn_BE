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
    private final PerformanceListHelper helper;
    private final PerformanceRecommendationRepository recommendationRepository;

    public GetPerformanceBrowseResponse getRecommendations(Member member) {
        String key = "recommend:" + member.getId();
        return helper.fetchWithCache(
                key,
                () -> recommendationRepository.findTopRecommendedIds(member.getId(), LIMIT));
    }
}
