package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.performance.common.domain.entity.Busking;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.exception.NoSuchPerformanceException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    default Performance getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchPerformanceException::new);
    }

    @Query("SELECT p FROM Busking p WHERE p.id = :id")
    Optional<Busking> findBuskingById(@Param("id") Long id);

    default Busking getByBuskingId(Long id) {
        return findBuskingById(id)
                .orElseThrow(NoSuchPerformanceException::new);
    }
}
