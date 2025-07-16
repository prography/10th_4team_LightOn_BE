package com.prography.lighton.performance.users.infrastructure.repository;

import java.util.List;
import org.springframework.util.StringUtils;

public interface RecentPerformanceRepository {

    List<Long> findRecentAll(int limit);

    List<Long> findRecentByGenre(String genre, int limit);

    default List<Long> findRecentIds(String genre, int limit) {
        if (StringUtils.hasText(genre)) {
            return findRecentByGenre(genre.trim().toLowerCase(), limit);
        }
        return findRecentAll(limit);
    }
}
