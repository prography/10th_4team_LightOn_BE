package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.exception.NoSuchPerformanceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
                AND p.status = true
                ORDER BY RAND()
                LIMIT 3
            """, nativeQuery = true)
    List<Performance> findRandomRecommendedWithinBox(double minLat, double maxLat,
                                                     double minLng, double maxLng);

    @Query("""
                SELECT p FROM Performance p
                JOIN FETCH p.location pl
                JOIN FETCH p.genres pg
                WHERE p.approvedAt >= :fromDate
                  AND p.location.latitude BETWEEN :minLat AND :maxLat
                  AND p.location.longitude BETWEEN :minLng AND :maxLng
                  AND p.status = true
            """)
    List<Performance> findRegisteredInLastWeekWithinBox(double minLat, double maxLat,
                                                        double minLng, double maxLng,
                                                        LocalDateTime fromDate);

    @Query("""
                SELECT DISTINCT p FROM Performance p
                JOIN FETCH p.location pl
                JOIN FETCH p.genres pg
                WHERE (
                    p.schedule.endDate < :targetDate
                    OR (
                        p.schedule.endDate = :targetDate AND p.schedule.endTime > CURRENT_TIME
                    )
                )
                AND p.location.latitude BETWEEN :minLat AND :maxLat
                AND p.location.longitude BETWEEN :minLng AND :maxLng
                AND p.status = true
            """)
    List<Performance> findClosingSoonWithinBox(
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLng") double minLng,
            @Param("maxLng") double maxLng,
            @Param("targetDate") LocalDate targetDate
    );

    @Query("""
                SELECT p
                FROM Performance p
                WHERE p.approveStatus = com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus.APPROVED
                  AND p.canceled = false
                  AND LOWER(p.info.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Performance> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
