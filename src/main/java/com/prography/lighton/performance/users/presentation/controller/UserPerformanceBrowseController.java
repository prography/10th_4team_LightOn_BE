package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.users.application.service.UserRecommendationService;
import com.prography.lighton.performance.users.presentation.dto.response.GetRecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/members/performances")
@RequiredArgsConstructor
public class UserPerformanceBrowseController {

    private final UserRecommendationService recommendationService;

    @GetMapping("/recommend")
    public ResponseEntity<ApiResult<GetRecommendationResponse>> GetUserRecommendService(
            @LoginMember Member member) {
        GetRecommendationResponse dto = recommendationService.getRecommendations(member);
        return ResponseEntity.ok(ApiUtils.success(dto));
    }
}
