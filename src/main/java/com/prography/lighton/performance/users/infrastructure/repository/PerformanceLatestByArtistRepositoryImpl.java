package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.performance.common.domain.entity.QPerformance;
import com.prography.lighton.performance.common.domain.entity.association.QPerformanceArtist;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PerformanceLatestByArtistRepositoryImpl implements PerformanceLatestByArtistRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<Long> findLatestUpcomingIdByArtist(long artistId) {
        QPerformance p = QPerformance.performance;
        QPerformanceArtist pa = QPerformanceArtist.performanceArtist;
        LocalDate today = LocalDate.now();

        Long latestId = query
                .select(p.id.max())
                .from(p)
                .join(p.artists, pa)
                .where(
                        pa.artist.id.eq(artistId),
                        p.status.isTrue(),
                        p.canceled.isFalse(),
                        p.approveStatus.eq(ApproveStatus.APPROVED),
                        p.schedule.endDate.goe(today)
                )
                .fetchOne();

        return Optional.ofNullable(latestId);
    }
}
