package com.prography.lighton.performance.users.infrastructure.repository;


import static com.prography.lighton.performance.common.domain.entity.QPerformance.performance;
import static com.prography.lighton.performance.common.domain.entity.association.QPerformanceArtist.performanceArtist;

import com.prography.lighton.performance.common.domain.entity.QPerformance;
import com.prography.lighton.performance.common.domain.entity.association.QPerformanceArtist;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PerformanceLatestByArtistRepositoryImpl implements PerformanceLatestByArtistRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Long> findLatestUpcomingIdsByArtists(List<Long> artistIds, LocalDate today) {

        QPerformance pSub = new QPerformance("pSub");
        QPerformanceArtist paSub = new QPerformanceArtist("paSub");

        var latestPerArtist = JPAExpressions
                .select(pSub.id.max())
                .from(pSub)
                .join(pSub.artists, paSub)
                .where(
                        paSub.artist.id.in(artistIds),
                        pSub.status.isTrue(),
                        pSub.canceled.isFalse(),
                        pSub.approveStatus.eq(ApproveStatus.APPROVED),
                        pSub.schedule.endDate.goe(today)
                )
                .groupBy(paSub.artist.id);

        return query
                .select(performance.id)
                .distinct()
                .from(performance)
                .join(performance.artists, performanceArtist)
                .where(
                        performanceArtist.artist.id.in(artistIds),
                        performance.id.in(latestPerArtist)
                )
                .fetch();
    }
}
