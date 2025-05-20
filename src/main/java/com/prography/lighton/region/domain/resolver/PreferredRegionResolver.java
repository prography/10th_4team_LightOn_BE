package com.prography.lighton.region.domain.resolver;

import com.prography.lighton.member.domain.entity.vo.PreferredRegion;
import com.prography.lighton.region.domain.entity.Region;
import com.prography.lighton.region.domain.entity.SubRegion;
import com.prography.lighton.region.infrastructure.repository.RegionRepository;
import com.prography.lighton.region.infrastructure.repository.SubRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
