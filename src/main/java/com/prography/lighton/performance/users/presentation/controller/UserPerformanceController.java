package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.enums.PerformanceFilterType;
import com.prography.lighton.performance.users.application.service.PerformanceService;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceMapListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/performances")
@RequiredArgsConstructor
public class UserPerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("/nearby")
    public ResponseEntity<ApiResult<GetPerformanceMapListResponseDTO>> getNearbyPerformances(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "50") int radius) {
        return ResponseEntity.ok(
                ApiUtils.success(performanceService.findNearbyPerformances(latitude, longitude, radius)));
    }

    @GetMapping("/highlight")
    public ResponseEntity<ApiResult<GetPerformanceMapListResponseDTO>> getFilteredPerformances(
            @RequestParam PerformanceFilterType type,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "50") int radius,
            @LoginMember Member member) {
        return ResponseEntity.ok(ApiUtils.success(
                performanceService.findFilteredPerformances(type, latitude, longitude, radius, member)));
    }
}
