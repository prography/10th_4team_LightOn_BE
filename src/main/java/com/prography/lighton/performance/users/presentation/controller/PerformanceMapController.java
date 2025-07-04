package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.enums.PerformanceFilterType;
import com.prography.lighton.performance.users.application.service.ArtistPerformanceService;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceMapListResponseDTO;
import com.prography.lighton.performance.users.presentation.dto.response.PerformanceSearchItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/performances")
@RequiredArgsConstructor
public class PerformanceMapController {

    private final ArtistPerformanceService artistPerformanceService;

    @GetMapping("/nearby")
    public ResponseEntity<ApiResult<GetPerformanceMapListResponseDTO>> getNearbyPerformances(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "50") int radius) {
        return ResponseEntity.ok(
                ApiUtils.success(artistPerformanceService.findNearbyPerformances(latitude, longitude, radius)));
    }

    @GetMapping("/highlight")
    public ResponseEntity<ApiResult<GetPerformanceMapListResponseDTO>> getFilteredPerformances(
            @RequestParam PerformanceFilterType type,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "50") int radius,
            @LoginMember Member member) {
        return ResponseEntity.ok(ApiUtils.success(
                artistPerformanceService.findFilteredPerformances(type, latitude, longitude, radius, member)));
    }

    @GetMapping
    public ResponseEntity<ApiUtils.ApiResult<?>> searchPerformances(
            @RequestParam String keyword,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<PerformanceSearchItemDTO> result = artistPerformanceService.searchByKeyword(keyword, pageable);

        return ResponseEntity.ok(ApiUtils.success(result));
    }
}
