package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.performance.users.infrastructure.dto.PerformanceSummary;
import java.util.List;

public interface PerformanceSummaryRepository {

    List<PerformanceSummary> findSummaries(List<Long> ids);
}
