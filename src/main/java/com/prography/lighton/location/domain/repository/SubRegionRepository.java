package com.prography.lighton.location.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.lighton.location.domain.entity.SubRegion;
import com.prography.lighton.location.exception.NoSuchRegionException;

public interface SubRegionRepository extends JpaRepository<SubRegion, Long> {

	Optional<SubRegion> findByName(String name);

	default SubRegion getBySubRegionName(String subRegionName) {
		return findByName(subRegionName)
				.orElseThrow(NoSuchRegionException::new);
	}
}
