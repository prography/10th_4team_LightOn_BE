package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.users.infrastructure.dto.PerformanceSummary;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRecommendationRepository;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceBrowseResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRecommendationService {

    private static final int CACHE_LIMIT = 50;

    private final UserRecommendationRedisService redisService;
    private final PerformanceRecommendationRepository recommendationRepository;

    public GetPerformanceBrowseResponse getRecommendations(Member member) {
        List<Long> performanceIds = getRecommendedIds(member.getId());
        List<PerformanceSummary> summaries = recommendationRepository.findSummaries(performanceIds);
        sortByGivenOrder(summaries, performanceIds);
        return GetPerformanceBrowseResponse.of(summaries);
    }

    private List<Long> getRecommendedIds(Long memberId) {
        List<Long> ids = redisService.getRecommendPerformanceIds(memberId);

        if (ids == null) {
            ids = recommendationRepository.findTopRecommendedIds(memberId, CACHE_LIMIT);
            redisService.putRecommendPerformanceIds(memberId, ids);
        }

        return ids;
    }

    private void sortByGivenOrder(List<PerformanceSummary> summaries, List<Long> ids) {
        Map<Long, Integer> order = IntStream.range(0, ids.size())
                .boxed()
                .collect(Collectors.toMap(ids::get, i -> i));
        summaries.sort(Comparator.comparingInt(s -> order.get(s.id())));
    }
}
