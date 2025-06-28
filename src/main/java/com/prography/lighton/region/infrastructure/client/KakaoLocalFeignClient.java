package com.prography.lighton.region.infrastructure.client;

import static com.prography.lighton.common.constant.AuthConstants.HEADER_AUTHORIZATION;
import static com.prography.lighton.common.constant.AuthConstants.HEADER_CONTENT_TYPE;

import com.prography.lighton.common.config.FeignRetryConfig;
import com.prography.lighton.region.application.dto.KakaoAddressSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kakaoLocal",
        url = "${kakao.local.base-url}",
        configuration = {FeignRetryConfig.class}
)
public interface KakaoLocalFeignClient {

    @GetMapping("/v2/local/search/address.json")
    KakaoAddressSearchResponse search(
            @RequestParam("query") String address,
            @RequestHeader(HEADER_AUTHORIZATION) String restApiKey,
            @RequestHeader(HEADER_CONTENT_TYPE) String contentType);
}