package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.genre.domain.entity.QGenre;
import com.prography.lighton.performance.common.domain.entity.QPerformance;
import com.prography.lighton.performance.common.domain.entity.association.QPerformanceGenre;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecentPerformanceRepositoryImpl implements RecentPerformanceRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Long> findRecentAll(int limit) {
        QPerformance p = QPerformance.performance;

        return query
                .select(p.id)
                .from(p)
                .where(
                        p.approveStatus.eq(ApproveStatus.APPROVED),
                        p.status.isTrue(),
                        p.canceled.isFalse()
                )
                .orderBy(p.createdAt.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<Long> findRecentByGenre(String genre, int limit) {
        QPerformance p = QPerformance.performance;
        QPerformanceGenre pg = QPerformanceGenre.performanceGenre;
        QGenre g = QGenre.genre;

        return query
                .select(p.id)
                .distinct()
                .from(p)
                .join(p.genres, pg)
                .join(pg.genre, g)
                .where(
                        p.approveStatus.eq(ApproveStatus.APPROVED),
                        p.status.isTrue(),
                        p.canceled.isFalse(),
                        g.name.eq(genre)
                )
                .orderBy(p.createdAt.desc())
                .limit(limit)
                .fetch();
    }
}
