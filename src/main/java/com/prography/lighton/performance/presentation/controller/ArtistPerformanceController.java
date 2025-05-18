package com.prography.lighton.performance.presentation.controller;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.performance.application.service.PerformanceService;
import com.prography.lighton.performance.presentation.dto.PerformanceRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artists/performances")
public class ArtistPerformanceController {

    private final PerformanceService performanceService;

    @PostMapping
    public ResponseEntity<ApiResult<String>> registerPerformance(@Valid PerformanceRegisterRequest request) {
        // 나중에 수정 필요
        Member member = Member.withId(1L);
        performanceService.registerPerformance(member, request);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

}
