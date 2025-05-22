package com.prography.lighton.performance.admin.presentation;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.performance.admin.application.ManagePerformanceApplicationUseCase;
import com.prography.lighton.performance.admin.application.PendingPerformanceQueryUseCase;
import com.prography.lighton.performance.admin.presentation.dto.request.ManagePerformanceApplicationRequestDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationListResponseDTO;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import java.util.List;
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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminPerformanceController {

    private final ManagePerformanceApplicationUseCase managePerformanceApplicationUseCase;
    private final PendingPerformanceQueryUseCase pendingPerformanceQueryUseCase;

    @GetMapping("/applications/performances")
    public ResponseEntity<ApiResult<GetPerformanceApplicationListResponseDTO>> getPerformanceApplicationList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<ApproveStatus> statuses
    ) {
        GetPerformanceApplicationListResponseDTO result =
                pendingPerformanceQueryUseCase.getAllPerformanceApplications(page, size, statuses);

        return ResponseEntity.ok(ApiUtils.success(result));
    }


    @GetMapping("/applications/performances/{performanceId}")
    public ResponseEntity<ApiUtils.ApiResult<GetPerformanceApplicationDetailResponseDTO>> getPerformanceApplicationList(
            @PathVariable Long performanceId) {
        return ResponseEntity.ok(ApiUtils.success(
                pendingPerformanceQueryUseCase.getPendingPerformanceDetail(performanceId)));
    }

    // 아티스트 신청 승인 API
    @PostMapping("/applications/performances/{performanceId}/approve")
    public ResponseEntity<ApiResult<?>> managePerformanceApplication(@PathVariable Long performanceId,
                                                                     @RequestBody
                                                                     ManagePerformanceApplicationRequestDTO request) {
        managePerformanceApplicationUseCase.managePerformanceApplication(performanceId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
