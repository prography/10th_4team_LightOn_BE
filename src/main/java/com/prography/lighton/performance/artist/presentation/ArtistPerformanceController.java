package com.prography.lighton.performance.artist.presentation;

import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationsListResponseDTO;
import com.prography.lighton.performance.artist.presentation.dto.response.GetPerformanceRequestsResponseDTO;
import com.prography.lighton.performance.common.domain.entity.enums.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artist/performances")
@RequiredArgsConstructor
public class ArtistPerformanceController {

    @GetMapping("/{performanceId}/requests")
    public ResponseEntity<ApiResult<GetPerformanceRequestsResponseDTO>> getPerformanceRequests(
            @PathVariable Long performanceId
    ) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/{performanceId}/requests/{requestId}")
    public ResponseEntity<ApiResult<Void>> managePerformanceRequest(
            @PathVariable Long performanceId,
            @PathVariable Long requestId,
            @RequestParam RequestStatus requestStatus
            ) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
