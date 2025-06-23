package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.users.application.service.UserPerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/performances")
@RequiredArgsConstructor
public class UserPerformanceController {

    private final UserPerformanceService userPerformanceService;

    @PostMapping("/{performanceId}")
    public ResponseEntity<ApiResult<String>> requestForPerformance(
            @PathVariable Long performanceId,
            @RequestParam Integer applyCount,
            @LoginMember Member member) {
        userPerformanceService.requestForPerformance(performanceId, applyCount, member);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }
}
