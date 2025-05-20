package com.prography.lighton.region.infrastructure.cache;

import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.region.domain.repository.SubRegionRepository;
import com.prography.lighton.region.exception.NoSuchRegionException;
import com.prography.lighton.region.infrastructure.repository.SubRegionRepository;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
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

        if (cache.isEmpty()) {
            throw new IllegalStateException("RegionCache 초기화 실패: 캐시 비어 있음");
        }

        log.info("지역 엔티티 캐시 초기화 성공 - size: {}", cache.size());
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
