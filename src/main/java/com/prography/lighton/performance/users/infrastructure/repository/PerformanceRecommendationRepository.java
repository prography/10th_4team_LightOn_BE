package com.prography.lighton.performance.users.infrastructure.repository;

import java.util.List;

public interface PerformanceRecommendationRepository {

    List<Long> findTopRecommendedIds(Long memberId, int limit);
}
