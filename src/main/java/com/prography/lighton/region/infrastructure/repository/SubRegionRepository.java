package com.prography.lighton.region.infrastructure.repository;

import com.prography.lighton.region.domain.entity.SubRegion;
import com.prography.lighton.region.exception.NoSuchRegionException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubRegionRepository extends JpaRepository<SubRegion, Long> {

    Optional<SubRegion> findByCode(Integer code);

    default SubRegion getByRegionCode(Integer code) {
        return findByCode(code)
                .orElseThrow(NoSuchRegionException::new);
    }
}
