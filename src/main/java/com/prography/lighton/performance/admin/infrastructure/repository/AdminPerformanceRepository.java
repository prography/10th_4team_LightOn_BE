package com.prography.lighton.performance.admin.infrastructure.repository;

import com.prography.lighton.performance.users.domain.entity.Performance;
import com.prography.lighton.performance.users.domain.entity.enums.ApproveStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminPerformanceRepository extends JpaRepository<Performance, Long> {

    Optional<Performance> findByIdAndApproveStatus(Long id, ApproveStatus approveStatus);

    @Query(value = """
            select distinct p from Performance p
            join fetch p.genres pg
            where p.approveStatus = :approveStatus
              and p.status = true
            """,
            countQuery = """
                    select count(p) from Performance p
                    where p.approveStatus = :approveStatus
                      and p.status = true
                    """)
    Page<Performance> findByApproveStatus(ApproveStatus approveStatus, Pageable pageable);

    @Query(value = """
            select distinct p from Performance p
            join fetch p.genres pg
            where p.approveStatus != :approveStatus
              and p.status = true
            """,
            countQuery = """
                    select count(p) from Performance p
                    where p.approveStatus != :approveStatus
                      and p.status = true
                    """)
    Page<Performance> findByUnapprovedPerformances(ApproveStatus approveStatus, Pageable pageable);
}
