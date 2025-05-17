package com.prography.lighton.region.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import com.prography.lighton.region.domain.entity.SubRegion;
import com.prography.lighton.region.exception.NoSuchRegionException;

public interface SubRegionRepository extends JpaRepository<SubRegion, Long> {

	Optional<SubRegion> findByCode(Integer code);

	default SubRegion getByRegionCode(Integer code) {
		return findByCode(code)
				.orElseThrow(() -> new NoSuchRegionException(HttpStatus.NOT_FOUND));
	}
}
