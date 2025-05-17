package com.prography.lighton.location.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.lighton.location.domain.entity.SubRegion;
import com.prography.lighton.location.exception.NoSuchRegionException;

public interface SubRegionRepository extends JpaRepository<SubRegion, Long> {

	Optional<SubRegion> findByCode(Integer code);

	default SubRegion getByRegionCode(Integer code) {
		return findByCode(code)
				.orElseThrow(NoSuchRegionException::new);
	}
}
