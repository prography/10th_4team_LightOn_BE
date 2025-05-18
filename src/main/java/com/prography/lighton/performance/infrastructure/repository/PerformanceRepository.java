package com.prography.lighton.performance.infrastructure.repository;

import com.prography.lighton.performance.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
}
