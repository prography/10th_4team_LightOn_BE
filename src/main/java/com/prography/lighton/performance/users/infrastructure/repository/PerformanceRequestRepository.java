package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import com.prography.lighton.performance.common.domain.exception.NoSuchPerformanceRequestException;
import java.time.LocalDateTime;
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

}
