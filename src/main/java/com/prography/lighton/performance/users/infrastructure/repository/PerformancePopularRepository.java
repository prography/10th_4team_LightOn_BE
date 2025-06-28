package com.prography.lighton.performance.users.infrastructure.repository;

import java.util.List;

public interface PerformancePopularRepository {

    List<Long> findTopPopularIds(String genre, int limit);
}
