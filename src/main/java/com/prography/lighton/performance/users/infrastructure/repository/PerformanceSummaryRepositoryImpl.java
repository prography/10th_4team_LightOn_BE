package com.prography.lighton.performance.users.infrastructure.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.Projections.list;

import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.QPerformance;
import com.prography.lighton.performance.common.domain.entity.association.QPerformanceGenre;
import com.prography.lighton.performance.users.infrastructure.dto.PerformanceSummary;
import com.prography.lighton.region.domain.entity.QSubRegion;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PerformanceSummaryRepositoryImpl implements PerformanceSummaryRepository {

    private final JPAQueryFactory query;

    @Override
    @Transactional(readOnly = true)
    public List<PerformanceSummary> findSummaries(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }

        PathBuilder<Performance> perf = new PathBuilder<>(Performance.class, "performance");
        NumberPath<Long> subRegionIdPath = perf
                .get("location", PathBuilder.class)
                .get("region", PathBuilder.class)
                .get("subRegion", PathBuilder.class)
                .getNumber("id", Long.class);

        QPerformance p = QPerformance.performance;
        QPerformanceGenre pg = QPerformanceGenre.performanceGenre;
        QSubRegion sr = QSubRegion.subRegion;

        return query
                .from(perf)
                .leftJoin(p.genres, pg)
                .leftJoin(sr)
                .on(sr.id.eq(subRegionIdPath))
                .where(
                        perf.getNumber("id", Long.class).in(ids),
                        perf.getBoolean("status").eq(true),
                        perf.getBoolean("canceled").eq(false)
                )
                .transform(
                        groupBy(perf.getNumber("id", Long.class)).list(
                                constructor(PerformanceSummary.class,
                                        perf.getNumber("id", Long.class),
                                        perf.get("info").get("title", String.class),
                                        perf.get("info").get("description", String.class),
                                        perf.get("info").get("posterUrl", String.class),
                                        perf.get("schedule").get("startDate", LocalDate.class),
                                        perf.get("schedule").get("startTime", LocalTime.class),
                                        perf.get("payment").get("isPaid", Boolean.class),
                                        sr.name,
                                        list(pg.genre.name)
                                )
                        )
                );
    }
}
