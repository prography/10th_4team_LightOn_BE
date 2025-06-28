package com.prography.lighton.performance.admin.presentation;

import com.prography.lighton.common.annotation.AdminOnly;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.performance.admin.application.GetPerformanceStatsUseCase;
import com.prography.lighton.performance.admin.application.ManagePerformanceApplicationUseCase;
import com.prography.lighton.performance.admin.application.PerformanceApplicationQueryUseCase;
import com.prography.lighton.performance.admin.presentation.dto.request.ManagePerformanceApplicationRequestDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationListResponseDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceStatsResponseDTO;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.presentation.dto.response.GetPerformanceDetailResponseDTO;
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

@AdminOnly
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminPerformanceController {

    private final ManagePerformanceApplicationUseCase managePerformanceApplicationUseCase;
    private final PerformanceApplicationQueryUseCase performanceApplicationQueryUseCase;
    private final GetPerformanceStatsUseCase getPerformanceStatsUseCase;

    @GetMapping("/applications/performances")
    public ResponseEntity<ApiResult<GetPerformanceApplicationListResponseDTO>> getPerformanceApplicationList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam Type type,
            @RequestParam(required = false) List<ApproveStatus> statuses
    ) {
        GetPerformanceApplicationListResponseDTO result =
                performanceApplicationQueryUseCase.getAllPerformanceApplications(page, size, type, statuses);

        return ResponseEntity.ok(ApiUtils.success(result));
    }


    @GetMapping("/applications/performances/{performanceId}")
    public ResponseEntity<ApiUtils.ApiResult<GetPerformanceDetailResponseDTO>> getPerformanceApplicationList(
            @PathVariable Long performanceId) {
        return ResponseEntity.ok(ApiUtils.success(
                performanceApplicationQueryUseCase.getPendingPerformanceDetail(performanceId)));
    }

    // 아티스트 신청 승인 API
    @PostMapping("/applications/performances/{performanceId}/approve")
    public ResponseEntity<ApiResult<?>> managePerformanceApplication(@PathVariable Long performanceId,
                                                                     @RequestBody
                                                                     ManagePerformanceApplicationRequestDTO request) {
        managePerformanceApplicationUseCase.managePerformanceApplication(performanceId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @GetMapping("/performances/stats")
    public ResponseEntity<ApiResult<GetPerformanceStatsResponseDTO>> getPerformanceStats() {
        return ResponseEntity.ok(ApiUtils.success(getPerformanceStatsUseCase.getPerformanceStats()));
    }
}
