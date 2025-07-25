package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Busking;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.exception.NoSuchPerformanceException;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    default Performance getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchPerformanceException::new);
    }

    default Performance getByIdWithPessimisticLock(Long id) {
        return findByIdWithPessimisticLock(id)
                .orElseThrow(NoSuchPerformanceException::new);
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Performance p WHERE p.id = :id")
    Optional<Performance> findByIdWithPessimisticLock(@Param("id") Long id);

    @Query("SELECT p FROM Busking p WHERE p.id = :id")
    Optional<Busking> findBuskingById(@Param("id") Long id);

    List<Performance> findAllByPerformer(Member performer);

    default Busking getByBuskingId(Long id) {
        return findBuskingById(id)
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

    @Query("""
                SELECT DISTINCT p
                                               FROM Performance p
                                               LEFT JOIN p.artists pa
                                               LEFT JOIN pa.artist a
                                                    WITH a.status = true
                                              WHERE p.status = true
                                                AND (p.performer = :member OR a.member = :member)
                                           ORDER BY p.createdAt DESC
            """)
    List<Performance> getMyRegisteredOrParticipatedPerformanceList(@Param("member") Member member);
}
