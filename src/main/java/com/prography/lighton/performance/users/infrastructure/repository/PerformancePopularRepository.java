package com.prography.lighton.performance.users.infrastructure.repository;

import java.util.List;
import org.springframework.util.StringUtils;

public interface PerformancePopularRepository {

    List<Long> findTopPopularAll(int limit);

    List<Long> findTopPopularByGenre(String genre, int limit);

    default List<Long> findTopPopularIds(String genre, int limit) {
        if (StringUtils.hasText(genre)) {
            return findTopPopularByGenre(genre.trim().toLowerCase(), limit);
        }
        return findTopPopularAll(limit);
    }
}
