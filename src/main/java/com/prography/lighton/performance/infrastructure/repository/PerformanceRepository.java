package com.prography.lighton.performance.infrastructure.repository;

import com.prography.lighton.performance.domain.entity.Performance;
import com.prography.lighton.performance.domain.exeption.NoSuchPerformanceException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    default Performance getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchPerformanceException::new);
    }
}
