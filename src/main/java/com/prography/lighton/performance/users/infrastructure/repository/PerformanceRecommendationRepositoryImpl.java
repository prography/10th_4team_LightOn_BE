package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.member.common.domain.entity.association.QPreferredGenre;
import com.prography.lighton.performance.common.domain.entity.QPerformance;
import com.prography.lighton.performance.common.domain.entity.association.QPerformanceGenre;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PerformanceRecommendationRepositoryImpl implements PerformanceRecommendationRepository {

    private final JPAQueryFactory query;

    @Override
    @Transactional(readOnly = true)
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
}
