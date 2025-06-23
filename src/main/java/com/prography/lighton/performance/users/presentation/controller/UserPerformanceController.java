package com.prography.lighton.performance.users.presentation.controller;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/{performanceId}")
    public ResponseEntity<ApiResult<?>> requestForPerformance(@PathVariable Long performanceId,
                                                              @RequestParam Long applyCount,
                                                              @LoginMember Member member) {

        return null;
    }

}
