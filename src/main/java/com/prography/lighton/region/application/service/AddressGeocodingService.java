package com.prography.lighton.region.application.service;

import com.prography.lighton.common.feign.ExternalApiCallException;
import com.prography.lighton.common.feign.SafeFeignExecutor;
import com.prography.lighton.region.application.dto.Coordinate;
import com.prography.lighton.region.application.dto.KakaoAddressSearchResponse;
import com.prography.lighton.region.infrastructure.client.KakaoLocalFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressGeocodingService {

    private final KakaoLocalFeignClient kakaoClient;

    public Coordinate geocode(String address) {
        KakaoAddressSearchResponse res = SafeFeignExecutor.run(
                () -> kakaoClient.search(address));

        return res.documents().stream()
                .findFirst()
                .map(doc -> new Coordinate(doc.x(), doc.y()))
                .orElseThrow(() -> new ExternalApiCallException());
    }
}
