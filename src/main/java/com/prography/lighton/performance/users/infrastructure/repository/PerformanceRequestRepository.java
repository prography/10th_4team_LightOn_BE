package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRequestRepository extends JpaRepository<PerformanceRequest, Long> {
}
