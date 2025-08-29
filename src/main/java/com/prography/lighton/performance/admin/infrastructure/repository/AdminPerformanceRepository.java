package com.prography.lighton.performance.admin.infrastructure.repository;

import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.domain.exception.NoSuchPerformanceException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminPerformanceRepository extends JpaRepository<Performance, Long> {


    @Query(value = """
            select distinct p from Performance p
            join fetch p.genres pg
            join fetch pg.genre g
            where p.approveStatus in :statuses
            and p.type = :type
            and p.status = true
            and p.canceled = false
            """,
            countQuery = """
                    select count(distinct p) from Performance p
                    where p.approveStatus in :statuses
                    and p.status = true
                    and p.canceled = false
                    """)
    Page<Performance> findByApproveStatusesAndType(@Param("type") Type type,
                                                   @Param("statuses") List<ApproveStatus> approveStatus,
                                                   Pageable pageable);

    Long countByApproveStatus(ApproveStatus approveStatus);

    @Query("""
            SELECT COUNT(p) FROM Performance p
            WHERE p.approveStatus = :approveStatus
            AND (
                p.schedule.endDate < CURRENT_DATE
                OR (p.schedule.endDate = CURRENT_DATE AND p.schedule.endTime <= CURRENT_TIME)
            )
            and p.status = true
            and p.canceled = false
            """)
    Long countEndedByApproveStatus(
            @Param("approveStatus") ApproveStatus approveStatus
    );

    default Performance getById(Long performanceId) {
        return findById(performanceId).orElseThrow(NoSuchPerformanceException::new);
    }
}
