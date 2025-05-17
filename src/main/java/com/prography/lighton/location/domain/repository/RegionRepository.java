package com.prography.lighton.location.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.lighton.location.domain.entity.Region;
import com.prography.lighton.location.exception.NoSuchRegionException;

public interface RegionRepository extends JpaRepository<Region, Long> {

	Optional<Region> findByName(String name);

	default Region getByRegionName(String regionName) {
		return findByName(regionName)
				.orElseThrow(NoSuchRegionException::new);
	}
}
