package com.prography.lighton.performance.users.infrastructure.repository;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

public interface PerformancePopularRepository {

    List<Long> findTopPopularAll(int limit);

    List<Long> findTopPopularByGenre(String genre, int limit);

    default List<Long> findTopPopularIds(String genre, int limit) {
        if (StringUtils.isBlank(genre)) {
            return findTopPopularAll(limit);
        }
        return findTopPopularByGenre(genre.trim().toLowerCase(), limit);
    }
}
