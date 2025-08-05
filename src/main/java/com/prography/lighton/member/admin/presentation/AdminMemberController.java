package com.prography.lighton.member.admin.presentation;

import com.prography.lighton.common.annotation.AdminOnly;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.admin.application.AdminMemberQueryService;
import com.prography.lighton.member.admin.presentation.dto.response.GetMemberStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

    private final AdminMemberQueryService adminMemberQueryService;

    @AdminOnly
    @GetMapping("/stats")
    public ResponseEntity<ApiResult<GetMemberStatsResponse>> getMemberStats() {
        return ResponseEntity.ok(ApiUtils.success(adminMemberQueryService.getMemberStats()));
    }
}
