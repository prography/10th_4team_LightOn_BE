package com.prography.lighton.region.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.lighton.region.domain.entity.Region;
import com.prography.lighton.region.exception.NoSuchRegionException;

public interface RegionRepository extends JpaRepository<Region, Long> {

	default Region getByRegionCode(Integer code) {
		return findById((long) ((code / 100) * 100))
				.orElseThrow(NoSuchRegionException::new);
	}
}