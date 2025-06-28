package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.performance.users.infrastructure.dto.PerformanceSummary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
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
    private static final String SUMMARY_SQL_BASE = """
                SELECT
                  p.id           AS perf_id,
                  p.title,
                  p.description,
                  p.poster_url,
                  p.start_date,
                  p.start_time,
                  p.is_paid,
                  sr.name        AS region_name,
                  g.name         AS genre_name
                FROM performance p
                LEFT JOIN performance_genre pg ON pg.performance_id = p.id
                LEFT JOIN genre g             ON g.id = pg.genre_id
                LEFT JOIN sub_region sr       ON sr.id = p.sub_region_id
                WHERE
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

    @Override
    @Transactional(readOnly = true)
    public List<PerformanceSummary> findSummaries(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }

        // 1) WHERE p.id IN (?, ?, ..., ?)
        String placeholders = ids.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));
        String sql = SUMMARY_SQL_BASE + " p.id IN (" + placeholders + ")";

        // 2) 네이티브 쿼리 생성 및 파라미터 바인딩
        Query q = em.createNativeQuery(sql);
        for (int i = 0; i < ids.size(); i++) {
            q.setParameter(i + 1, ids.get(i)); // JPA native는 1-based positional
        }

        @SuppressWarnings("unchecked")
        List<Object[]> rows = q.getResultList();

        // 3) 결과 집계 & DTO 변환 (위와 동일)
        Map<Long, SummaryBuilder> map = new HashMap<>();
        for (Object[] r : rows) {
            Long id = ((Number) r[0]).longValue();
            String title = (String) r[1];
            String desc = (String) r[2];
            String posterUrl = (String) r[3];
            LocalDate startDate = r[4] != null ? ((java.sql.Date) r[4]).toLocalDate() : null;
            LocalTime startTime = r[5] != null ? ((java.sql.Time) r[5]).toLocalTime() : null;
            Object paidVal = r[6];
            Boolean isPaid = paidVal != null
                    ? (Boolean) paidVal
                    : null;
            String regionName = (String) r[7];
            String genreName = (String) r[8];

            map.computeIfAbsent(id, k -> new SummaryBuilder(
                            id, title, desc, posterUrl, startDate, startTime, isPaid, regionName))
                    .addGenre(genreName);
        }

        // 4) ids 순서 유지하며 DTO 리스트 반환
        return ids.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .map(SummaryBuilder::build)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 내부 DTO 빌더 (위와 동일)
     **/
    private static class SummaryBuilder {
        private final Long id;
        private final String title, desc, posterUrl, regionName;
        private final LocalDate startDate;
        private final LocalTime startTime;
        private final Boolean isPaid;
        private final List<String> genres = new ArrayList<>();

        SummaryBuilder(Long id, String title, String desc, String posterUrl,
                       LocalDate sd, LocalTime st, Boolean isPaid, String regionName) {
            this.id = id;
            this.title = title;
            this.desc = desc;
            this.posterUrl = posterUrl;
            this.startDate = sd;
            this.startTime = st;
            this.isPaid = isPaid;
            this.regionName = regionName;
        }

        void addGenre(String g) {
            if (g != null) {
                genres.add(g);
            }
        }

        PerformanceSummary build() {
            return new PerformanceSummary(id, title, desc, posterUrl,
                    startDate, startTime, isPaid, regionName, genres);
        }
    }
}
