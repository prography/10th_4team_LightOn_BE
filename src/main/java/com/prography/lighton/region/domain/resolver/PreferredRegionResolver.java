package com.prography.lighton.region.domain.resolver;

import org.springframework.stereotype.Component;

import com.prography.lighton.member.domain.entity.vo.PreferredRegion;
import com.prography.lighton.region.domain.entity.Region;
import com.prography.lighton.region.domain.entity.SubRegion;
import com.prography.lighton.region.domain.repository.RegionRepository;
import com.prography.lighton.region.domain.repository.SubRegionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PreferredRegionResolver {
	private final RegionRepository regionRepository;
	private final SubRegionRepository subRegionRepository;

	public PreferredRegion resolve(Integer regionCode) {
		Region region = regionRepository.getByRegionCode(getParentRegionCode(regionCode));
		SubRegion subRegion = subRegionRepository.getByRegionCode(regionCode);
		return PreferredRegion.of(region, subRegion);
	}

	private Integer getParentRegionCode(Integer regionCode) {
		return (regionCode / 100) * 100;
	}
}
