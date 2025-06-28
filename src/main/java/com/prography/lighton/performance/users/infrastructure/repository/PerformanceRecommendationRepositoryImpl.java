package com.prography.lighton.performance.users.infrastructure.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.core.types.Projections.constructor;

import com.prography.lighton.member.common.domain.entity.association.QPreferredGenre;
import com.prography.lighton.performance.common.domain.entity.QPerformance;
import com.prography.lighton.performance.common.domain.entity.association.QPerformanceGenre;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.users.infrastructure.dto.PerformanceSummary;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PerformanceRecommendationRepositoryImpl
        implements PerformanceRecommendationRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Long> findTopRecommendedIds(Long memberId, int limit) {
        QPerformance p = QPerformance.performance;
        QPerformanceGenre pg = QPerformanceGenre.performanceGenre;
        QPreferredGenre mpg = QPreferredGenre.preferredGenre;

        return query
                .select(p.id)
                .from(p)
                .join(p.genres, pg)
                .join(mpg).on(pg.genre.id.eq(mpg.genre.id))
                .where(
                        mpg.member.id.eq(memberId),
                        p.approveStatus.eq(ApproveStatus.APPROVED),
                        p.schedule.endDate.goe(LocalDate.now())
                )
                .groupBy(p.id)
                .orderBy(
                        pg.genre.id.count().desc(),
                        p.createdAt.desc()
                )
                .limit(limit)
                .fetch();
    }

    @Override
    public List<PerformanceSummary> findSummaries(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }

        QPerformance p = QPerformance.performance;
        QPerformanceGenre pg = QPerformanceGenre.performanceGenre;

        return query
                .from(p)
                .leftJoin(p.genres, pg).fetchJoin()
                .where(p.id.in(ids))
                .transform(
                        groupBy(p.id).list(
                                constructor(PerformanceSummary.class,
                                        p.id,
                                        p.info.title,
                                        p.info.description,
                                        p.info.posterUrl,
                                        p.schedule.startDate,
                                        p.schedule.startTime,
                                        p.payment.isPaid,
                                        p.location.region.subRegion.name,
                                        list(pg.genre.name)
                                )
                        )
                );
    }
}
