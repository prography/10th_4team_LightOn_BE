package com.prography.lighton.location.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.lighton.location.domain.entity.Region;
import com.prography.lighton.location.exception.NoSuchRegionException;

public interface RegionRepository extends JpaRepository<Region, Long> {

	default Region getByRegionCode(Integer code) {
		System.out.println((long) ((code / 100) * 100));
		return findById((long) ((code / 100) * 100))
				.orElseThrow(NoSuchRegionException::new);
	}
}