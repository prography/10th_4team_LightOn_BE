package com.prography.lighton.performance.admin.infrastructure.repository;

import com.prography.lighton.performance.users.domain.entity.Performance;
import com.prography.lighton.performance.users.domain.entity.enums.ApproveStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPerformanceRepository extends JpaRepository<Performance, Long> {

    Optional<Performance> findByIdAndApproveStatus(Long id, ApproveStatus approveStatus);
}
