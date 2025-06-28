package com.prography.lighton.performance.users.infrastructure.repository;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PerformancePopularRepositoryImpl implements PerformancePopularRepository {

    private static final String POPULAR_ALL_SQL = """
            SELECT p.id
            FROM performance p
            WHERE p.approve_status = 'APPROVED'
              AND p.end_date       >= CURRENT_DATE
            ORDER BY p.like_count DESC,
                     p.view_count DESC
            LIMIT :limit
            """;

    private static final String POPULAR_BY_GENRE_SQL = """
            SELECT p.id
            FROM performance p
            JOIN performance_genre pg ON pg.performance_id = p.id
            JOIN genre g              ON g.id = pg.genre_id
            WHERE p.approve_status = 'APPROVED'
              AND p.end_date       >= CURRENT_DATE
              AND g.name = :genre
            GROUP BY p.id
            ORDER BY p.like_count DESC,
                     p.view_count DESC
            LIMIT :limit
            """;

    private final EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Long> findTopPopularIds(String genre, int limit) {
        Query q;
        if (StringUtils.isBlank(genre)) {
            q = em.createNativeQuery(POPULAR_ALL_SQL);
        } else {
            q = em.createNativeQuery(POPULAR_BY_GENRE_SQL)
                    .setParameter("genre", genre.trim().toLowerCase());
        }
        q.setParameter("limit", limit);

        @SuppressWarnings("unchecked")
        List<Number> rows = q.getResultList();
        return rows.stream().map(Number::longValue).toList();
    }
}
