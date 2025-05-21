package com.prography.lighton.member.presentation;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.presentation.dto.request.admin.ManagePerformanceApplicationRequestDTO;
import com.prography.lighton.member.presentation.dto.response.admin.performance.GetPerformanceApplicationDetailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.admin.performance.GetPerformanceApplicationListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class ManagePerformanceApplicationController {


    @GetMapping("/application/performances")
    public ResponseEntity<ApiResult<GetPerformanceApplicationListResponseDTO>> getPerformanceApplicationList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }


    @GetMapping("/applications/performances/{performanceId}")
    public ResponseEntity<ApiUtils.ApiResult<GetPerformanceApplicationDetailResponseDTO>> getPerformanceApplicationList(
            @PathVariable Long performanceId) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 아티스트 신청 승인 API
    @PostMapping("/applications/performances/{performanceId}/approve")
    public ResponseEntity<ApiResult<?>> managePerformanceApplication(@PathVariable Long performanceId,
                                                                     @RequestBody
                                                                     ManagePerformanceApplicationRequestDTO request) {
        return ResponseEntity.ok(ApiUtils.success());
    }
}
