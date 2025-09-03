package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.users.application.service.UserPerformanceLikeService;
import com.prography.lighton.performance.users.presentation.dto.response.LikePerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/performances")
public class PerformanceLikeController {

    private final UserPerformanceLikeService likeService;

    @GetMapping("/{performanceId}/like")
    public ResponseEntity<ApiResult<LikePerformanceResponse>> getPerformanceLikeStatus(
            @LoginMember Member member,
            @PathVariable Long performanceId) {

        return ResponseEntity.ok(
                ApiUtils.success(likeService.getPerformanceLikeStatus(member, performanceId))
        );
    }

    @PostMapping("/{performanceId}/like")
    public ResponseEntity<ApiResult<LikePerformanceResponse>> likeOrUnlikePerformance(
            @LoginMember Member member,
            @PathVariable Long performanceId) {

        LikePerformanceResponse response = likeService.likeOrUnlikePerformance(member, performanceId);
        return ResponseEntity.ok(
                ApiUtils.success(response)
        );
    }
}

