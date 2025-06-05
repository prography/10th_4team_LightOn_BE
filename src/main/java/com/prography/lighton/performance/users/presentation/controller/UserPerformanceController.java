package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceMapListResponseDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/performances/")
public class UserPerformanceController {

    @GetMapping("/nearby")
    public ResponseEntity<ApiResult<List<GetPerformanceMapListResponseDTO>>> getNearbyPerformances(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "50") int radius) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 2. 추천 필터 적용 조회 (로그인 필요)
    @GetMapping("/highlight")
    public ResponseEntity<ApiResult<List<GetPerformanceMapListResponseDTO>>> getFilteredPerformances(
            @RequestParam String type,
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "50") int radius,
            @LoginMember Member member) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
