package com.prography.lighton.performance.artist.presentation;

import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.artist.application.ArtistPerformanceService;
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

    private final ArtistPerformanceService artistPerformanceService;

    @GetMapping("/{performanceId}/requests")
    public ResponseEntity<ApiResult<GetPerformanceRequestsResponseDTO>> getPerformanceRequests(
            @PathVariable Long performanceId,
            @LoginMember Member member
    ) {
        return ResponseEntity.ok(
                ApiUtils.success(artistPerformanceService.getPerformanceRequests(performanceId, member)));
    }

    @PostMapping("/{performanceId}/requests")
    public ResponseEntity<ApiResult<?>> managePerformanceRequest(
            @PathVariable Long performanceId,
            @LoginMember Member member,
            @RequestParam RequestStatus requestStatus
    ) {
        artistPerformanceService.managePerformanceRequest(performanceId, member, requestStatus);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
