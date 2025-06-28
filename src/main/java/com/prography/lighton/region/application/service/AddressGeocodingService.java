package com.prography.lighton.region.application.service;

import static com.prography.lighton.common.constant.AuthConstants.APPLICATION_JSON;
import static com.prography.lighton.common.constant.AuthConstants.KAKAO_AUTHORIZATION;

import com.prography.lighton.common.feign.ExternalApiCallException;
import com.prography.lighton.common.feign.SafeFeignExecutor;
import com.prography.lighton.region.application.dto.Coordinate;
import com.prography.lighton.region.application.dto.KakaoAddressSearchResponse;
import com.prography.lighton.region.infrastructure.client.KakaoLocalFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressGeocodingService {

    @Value("${kakao.local.rest-api-key}")
    private String restApiKey;

    private final KakaoLocalFeignClient kakaoClient;

    public Coordinate geocode(String address) {
        KakaoAddressSearchResponse res = SafeFeignExecutor.run(
                () -> kakaoClient.search(address, KAKAO_AUTHORIZATION + restApiKey, APPLICATION_JSON));

        return res.documents().stream()
                .findFirst()
                .map(doc -> new Coordinate(doc.x(), doc.y()))
                .orElseThrow(() -> new ExternalApiCallException());
    }
}
