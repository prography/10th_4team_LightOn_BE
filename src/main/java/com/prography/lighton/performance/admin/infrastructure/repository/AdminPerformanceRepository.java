package com.prography.lighton.performance.admin.infrastructure.repository;

import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminPerformanceRepository extends JpaRepository<Performance, Long> {

    @Query("""
                select p from Performance p
                join fetch p.genres pg
                where p.id = :id
                  and p.approveStatus = :approveStatus
                  and p.status = true
            """)
    Optional<Performance> findByIdAndApproveStatus(Long id, ApproveStatus approveStatus);


    @Query(value = """
            select distinct p from Performance p
            join fetch p.genres pg
            join fetch pg.genre g
            where p.approveStatus in :statuses
              and p.status = true
            """,
            countQuery = """
                    select count(distinct p) from Performance p
                    where p.approveStatus in :statuses
                      and p.status = true
                    """)
    Page<Performance> findByApproveStatuses(@Param("statues") List<ApproveStatus> approveStatus, Pageable pageable);
}
