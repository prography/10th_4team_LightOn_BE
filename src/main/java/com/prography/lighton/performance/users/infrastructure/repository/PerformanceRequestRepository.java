package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import com.prography.lighton.performance.common.domain.exception.NoSuchPerformanceRequestException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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


    @Query(value = """
                SELECT sr.code
                FROM performance_request pr
                JOIN performance p ON pr.performance_id = p.id
                JOIN sub_region sr ON p.sub_region_id = sr.id
                WHERE pr.member_id = :memberId
                  AND pr.request_status = 'APPROVED'
                GROUP BY sr.id
                ORDER BY COUNT(pr.id) DESC
                LIMIT 1
            """, nativeQuery = true)
    Integer findTopSubRegionId(@Param("memberId") Long memberId);

}
