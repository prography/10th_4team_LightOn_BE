package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.performance.users.domain.entity.Performance;
import com.prography.lighton.performance.users.domain.exception.NoSuchPerformanceException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    default Performance getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchPerformanceException::new);
    }
}
