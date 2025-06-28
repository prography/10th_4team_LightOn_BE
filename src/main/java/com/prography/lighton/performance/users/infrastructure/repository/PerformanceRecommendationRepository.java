package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.performance.users.infrastructure.dto.PerformanceSummary;
import java.util.List;

public interface PerformanceRecommendationRepository {

    List<Long> findTopRecommendedIds(Long memberId, int limit);

    List<PerformanceSummary> findSummaries(List<Long> ids);
}
