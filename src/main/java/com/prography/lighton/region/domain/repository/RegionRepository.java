package com.prography.lighton.region.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import com.prography.lighton.region.domain.entity.Region;
import com.prography.lighton.region.exception.NoSuchRegionException;

public interface RegionRepository extends JpaRepository<Region, Long> {

	default Region getByRegionCode(Integer code) {
		return findById((long) code)
				.orElseThrow(NoSuchRegionException::new);
	}
}
