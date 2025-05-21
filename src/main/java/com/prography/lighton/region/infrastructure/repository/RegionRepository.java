package com.prography.lighton.region.domain.repository;

import com.prography.lighton.region.domain.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
