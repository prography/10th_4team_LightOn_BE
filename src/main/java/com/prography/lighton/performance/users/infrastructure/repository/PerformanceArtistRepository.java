package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.performance.common.domain.entity.association.PerformanceArtist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PerformanceArtistRepository extends JpaRepository<PerformanceArtist, Long> {

    @Query("""
            SELECT pa FROM PerformanceArtist pa
            JOIN FETCH pa.artist a
            WHERE pa.performance.id IN :performanceIds
              AND pa.status = true
              AND a.status = true
              AND a.approveStatus = 'APPROVED'
            """)
    List<PerformanceArtist> findAllByPerformances(List<Long> performanceIds);
}
