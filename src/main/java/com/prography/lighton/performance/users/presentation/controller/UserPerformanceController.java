package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.presentation.dto.response.GetPerformanceDetailResponseDTO;
import com.prography.lighton.performance.users.application.service.UserPerformanceService;
import com.prography.lighton.performance.users.presentation.dto.response.GetMyPerformanceStatsResponseDTO;
import com.prography.lighton.performance.users.presentation.dto.response.GetMyRegisteredPerformanceListResponseDTO;
import com.prography.lighton.performance.users.presentation.dto.response.GetMyRequestedPerformanceListResponseDTO;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformancePaymentInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/{performanceId}")
    public ResponseEntity<ApiResult<GetPerformanceDetailResponseDTO>> getPerformanceDetail(
            @PathVariable Long performanceId) {
        return ResponseEntity.ok(ApiUtils.success(userPerformanceService.getPerformanceDetail(performanceId)));
    }

    @GetMapping("/{performanceId}/payment")
    public ResponseEntity<ApiResult<GetPerformancePaymentInfoResponse>> getPerformancePayment(
            @PathVariable Long performanceId,
            @RequestParam Integer applySeats
    ) {
        return ResponseEntity.ok(
                ApiUtils.success(userPerformanceService.getPerformancePaymentDetail(performanceId, applySeats)));
    }

    @PostMapping("/{performanceId}/request")
    public ResponseEntity<ApiResult<GetPerformancePaymentInfoResponse>> requestForPerformance(
            @PathVariable Long performanceId,
            @RequestParam Integer applySeats,
            @LoginMember Member member) {
        return ResponseEntity.ok(ApiUtils.success(userPerformanceService.requestForPerformance(
                performanceId, applySeats, member
        )));
    }

    @PostMapping("/{performanceId}/cancel")
    public ResponseEntity<ApiResult<String>> cancelPerformanceRequest(
            @PathVariable Long performanceId,
            @LoginMember Member member) {
        userPerformanceService.cancelPerformanceRequest(performanceId, member);
        return ResponseEntity.ok(ApiUtils.success());
    }

    // 내가 등록한 공연
    @GetMapping("/my-page/registered")
    public ResponseEntity<ApiResult<GetMyRegisteredPerformanceListResponseDTO>> getMyRegisteredPerformanceList(
            @LoginMember Member member) {
        return ResponseEntity.ok(ApiUtils.success(
                userPerformanceService.getMyRegisteredPerformanceList(member)));
    }

    // 내가 신청한 공연
    @GetMapping("/my-page/requested")
    public ResponseEntity<ApiResult<GetMyRequestedPerformanceListResponseDTO>> getMyRequestedPerformanceList(
            @LoginMember Member member) {
        return ResponseEntity.ok(ApiUtils.success(
                userPerformanceService.getMyRequestedPerformanceList(member)));
    }

    @GetMapping("/my-page/stats")
    public ResponseEntity<ApiResult<GetMyPerformanceStatsResponseDTO>> getMyPerformanceStats(
            @LoginMember Member member) {
        return ResponseEntity.ok(ApiUtils.success(
                userPerformanceService.getMyPerformanceStats(member)));
    }

}
