package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.genre.domain.entity.QGenre;
import com.prography.lighton.performance.common.domain.entity.QPerformance;
import com.prography.lighton.performance.common.domain.entity.association.QPerformanceGenre;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PerformancePopularRepositoryImpl implements PerformancePopularRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Long> findTopPopularAll(int limit) {
        QPerformance p = QPerformance.performance;
        LocalDate today = LocalDate.now();

        return query
                .select(p.id)
                .from(p)
                .where(
                        p.approveStatus.eq(ApproveStatus.APPROVED),
                        p.schedule.endDate.goe(today),
                        p.status.isTrue(),
                        p.canceled.isFalse()
                )
                .orderBy(p.likeCount.desc(), p.viewCount.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<Long> findTopPopularByGenre(String genre, int limit) {
        QPerformance p = QPerformance.performance;
        QPerformanceGenre pg = QPerformanceGenre.performanceGenre;
        QGenre g = QGenre.genre;
        LocalDate today = LocalDate.now();

        return query
                .select(p.id)
                .distinct()
                .from(p)
                .join(p.genres, pg)
                .join(pg.genre, g)
                .where(
                        p.approveStatus.eq(ApproveStatus.APPROVED),
                        p.schedule.endDate.goe(today),
                        p.status.isTrue(),
                        p.canceled.isFalse(),
                        g.name.eq(genre)
                )
                .orderBy(p.likeCount.desc(), p.viewCount.desc())
                .limit(limit)
                .fetch();
    }
}
