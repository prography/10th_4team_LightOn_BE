package com.prography.lighton.region.infrastructure.repository;

import com.prography.lighton.region.domain.entity.SubRegion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubRegionRepository extends JpaRepository<SubRegion, Long> {

    @Query("""
                SELECT sr
                FROM SubRegion sr
                JOIN FETCH sr.region r
                WHERE sr.status = true
                  AND r.status  = true
            """)
    List<SubRegion> findAllActiveWithRegion();
}
