package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import com.prography.lighton.performance.common.domain.exception.NoSuchPerformanceRequestException;
import com.prography.lighton.region.domain.entity.SubRegion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRequestRepository extends JpaRepository<PerformanceRequest, Long> {

    boolean existsByMemberAndPerformance(Member member, Performance performance);

    Optional<PerformanceRequest> findByMemberAndPerformance(Member member, Performance performance);

    default PerformanceRequest getByMemberAndPerformance(Member member, Performance performance) {
        return findByMemberAndPerformance(member, performance)
                .orElseThrow(NoSuchPerformanceRequestException::new);
    }

    @Modifying
    @Query("""
                UPDATE PerformanceRequest pr
                SET pr.requestStatus = 'REJECTED'
                WHERE pr.requestStatus = 'PENDING'
                  AND pr.createdAt <= :threshold
            """)
    void rejectExpiredRequests(@Param("threshold") LocalDateTime threshold);

    @Query("""
                SELECT pr FROM PerformanceRequest pr
                JOIN FETCH pr.performance p
                WHERE pr.member = :member
                AND p.status = true
                ORDER BY pr.createdAt DESC
            """)
    List<PerformanceRequest> getMyRequestedPerformanceList(@Param("member") Member member);

    @Query("""
                SELECT COUNT(pr)
                FROM PerformanceRequest pr
                JOIN pr.performance p
                WHERE pr.member = :member
                  AND p.status = true
                  AND pr.requestStatus = 'APPROVED'
                  AND (
                        p.schedule.endDate < :currentDate
                        OR (p.schedule.endDate = :currentDate AND p.schedule.endTime <= :currentTime)
                      )
            """)
    Integer countMyPerformanceApply(@Param("member") Member member,
                                    @Param("currentDate") LocalDate currentDate,
                                    @Param("currentTime") LocalTime currentTime);


    @Query("""
                SELECT pr.performance.location.region.subRegion
                FROM PerformanceRequest pr
                WHERE pr.member = :member
                  AND pr.requestStatus = 'APPROVED'
                GROUP BY pr.performance.location.region.subRegion
                ORDER BY COUNT(pr.id) DESC
            """)
    List<SubRegion> findTopSubRegion(@Param("member") Member member, Pageable pageable);
}
