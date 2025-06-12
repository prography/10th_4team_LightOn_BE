package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.exception.NoSuchPerformanceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    default Performance getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchPerformanceException::new);
    }

    @Query("""
                SELECT p FROM Performance p
                JOIN FETCH p.location pl
                JOIN FETCH p.genres pg
                WHERE p.location.latitude BETWEEN :minLat AND :maxLat
                  AND p.location.longitude BETWEEN :minLng AND :maxLng
                  AND p.status = true
            """)
    List<Performance> findRoughlyWithinBox(double minLat, double maxLat, double minLng, double maxLng);

    @Query(value = """
                SELECT p.* FROM performance p
                JOIN performance_location pl ON p.location_id = pl.id
                JOIN performance_genre pg ON p.id = pg.performance_id
                WHERE p.latitude BETWEEN :minLat AND :maxLat
                  AND p.longitude BETWEEN :minLng AND :maxLng
                ORDER BY RAND()
                LIMIT 3
            """, nativeQuery = true)
    List<Performance> findRandomRecommendedWithinBox(double minLat, double maxLat,
                                                     double minLng, double maxLng);

    @Query("""
                SELECT p FROM Performance p
                JOIN FETCH p.location pl
                JOIN FETCH p.genres pg
                WHERE p.createdAt >= :fromDate
                  AND p.location.latitude BETWEEN :minLat AND :maxLat
                  AND p.location.longitude BETWEEN :minLng AND :maxLng
            """)
    List<Performance> findRegisteredInLastWeekWithinBox(double minLat, double maxLat,
                                                        double minLng, double maxLng,
                                                        LocalDateTime fromDate);

    @Query("""
                SELECT p FROM Performance p
                JOIN FETCH p.location pl
                JOIN FETCH p.genres pg
                WHERE p.schedule.startDate = :targetDate
                  AND p.location.latitude BETWEEN :minLat AND :maxLat
                  AND p.location.longitude BETWEEN :minLng AND :maxLng
            """)
    List<Performance> findClosingSoonWithinBox(double minLat, double maxLat,
                                               double minLng, double maxLng,
                                               LocalDate targetDate);
}
