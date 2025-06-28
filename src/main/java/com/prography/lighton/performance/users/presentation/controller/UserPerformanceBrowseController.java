package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.users.application.service.UserPopularService;
import com.prography.lighton.performance.users.application.service.UserRecommendationService;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformanceBrowseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/members/performances")
@RequiredArgsConstructor
public class UserPerformanceBrowseController {

    private final UserRecommendationService recommendationService;
    private final UserPopularService popularService;

    @GetMapping("/recommend")
    public ResponseEntity<ApiResult<GetPerformanceBrowseResponse>> GetUserRecommendService(
            @LoginMember Member member) {
        GetPerformanceBrowseResponse dto = recommendationService.getRecommendations(member);
        return ResponseEntity.ok(ApiUtils.success(dto));
    }

    @GetMapping("/popular")
    public ResponseEntity<ApiUtils.ApiResult<GetPerformanceBrowseResponse>> popular(
            @RequestParam(required = false) String genre) {
        GetPerformanceBrowseResponse dto = popularService.getPopular(genre);
        return ResponseEntity.ok(ApiUtils.success(dto));
    }
}
