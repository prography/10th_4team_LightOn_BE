package com.prography.lighton.region.domain.resolver;

import com.prography.lighton.common.vo.RegionInfo;
import com.prography.lighton.region.domain.entity.Region;
import com.prography.lighton.region.domain.entity.SubRegion;
import com.prography.lighton.region.domain.repository.RegionRepository;
import com.prography.lighton.region.domain.repository.SubRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegionResolver {
    private final RegionRepository regionRepository;
    private final SubRegionRepository subRegionRepository;

    public RegionInfo resolve(Integer regionCode) {
        Region region = regionRepository.getByRegionCode(getParentRegionCode(regionCode));
        SubRegion subRegion = subRegionRepository.getByRegionCode(regionCode);
        return RegionInfo.of(region, subRegion);
    }

    private Integer getParentRegionCode(Integer regionCode) {
        return (regionCode / 100) * 100;
    }
}
