package com.prography.lighton.performance.users.infrastructure.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PerformanceRecommendationRepositoryImpl implements PerformanceRecommendationRepository {

    private static final String RECOMMENDED_SQL = """
            SELECT p.id
            FROM performance p
            LEFT JOIN performance_genre pg ON pg.performance_id = p.id
            LEFT JOIN preferred_genre mpg
                   ON mpg.genre_id = pg.genre_id
                  AND mpg.member_id = :memberId
            WHERE p.approve_status = 'APPROVED'
              AND p.end_date >= CURRENT_DATE
            GROUP BY p.id
            ORDER BY COUNT(mpg.genre_id) DESC,  -- 매칭 수로 정렬
                     p.created_at DESC
            
            """;

    private final EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Long> findTopRecommendedIds(Long memberId, int limit) {
        @SuppressWarnings("unchecked")
        List<Number> rows = em.createNativeQuery(RECOMMENDED_SQL)
                .setParameter("memberId", memberId)
                .setMaxResults(limit)
                .getResultList();

        return rows.stream().map(Number::longValue).toList();
    }
}
