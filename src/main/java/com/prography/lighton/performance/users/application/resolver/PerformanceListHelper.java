package com.prography.lighton.performance.users.application.resolver;

import com.prography.lighton.performance.users.application.service.PerformanceRedisService;
import com.prography.lighton.performance.users.infrastructure.dto.PerformanceSummary;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceSummaryRepository;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceBrowseResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformanceListHelper {

    private final PerformanceRedisService cache;
    private final PerformanceSummaryRepository summaryRepo;

    public GetPerformanceBrowseResponse fetchWithCache(
            String cacheKey,
            Supplier<List<Long>> idSupplier) {

        List<Long> ids = cache.get(cacheKey);

        if (ids == null) {
            ids = idSupplier.get();
            cache.put(cacheKey, ids);
        }

        if (ids.isEmpty()) {
            return new GetPerformanceBrowseResponse(List.of());
        }

        List<PerformanceSummary> summaries = summaryRepo.findSummaries(ids);

        Map<Long, Integer> order = IntStream.range(0, ids.size())
                .boxed()
                .collect(Collectors.toMap(ids::get, i -> i));
        summaries.sort(Comparator.comparingInt(s -> order.get(s.id())));

        return GetPerformanceBrowseResponse.of(summaries);
    }
}

