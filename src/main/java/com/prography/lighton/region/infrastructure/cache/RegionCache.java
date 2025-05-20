package com.prography.lighton.region.infrastructure.cache;

import com.prography.lighton.common.vo.RegionInfo;
import com.prography.lighton.region.exception.NoSuchRegionException;
import com.prography.lighton.region.infrastructure.repository.SubRegionRepository;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegionCache {
    private final SubRegionRepository subRegionRepository;
    private final Map<Integer, RegionInfo> cache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        subRegionRepository.findAllActiveWithRegion()
                .forEach(sr ->
                        cache.put(sr.getCode(), RegionInfo.of(sr.getRegion(), sr))
                );
    }

    public RegionInfo getRegionInfoByCode(Integer regionCode) {
        RegionInfo info = cache.get(regionCode);
        if (info == null) {
            throw new NoSuchRegionException();
        }
        return info;
    }

    private Integer getParentRegionCode(Integer regionCode) {
        return (regionCode / 100) * 100;
    }
}
