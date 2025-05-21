package com.prography.lighton.member.presentation;

import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.application.admin.PendingArtistQueryUseCase;
import com.prography.lighton.member.presentation.dto.request.ApproveArtistRequestDTO;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistDetailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistListResponseDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final PendingArtistQueryUseCase pendingArtistQueryUseCase;

    // 신청 처리 대기 아티스트 정보 상세 조회 API
    @GetMapping("/applications/artists")
    public ResponseEntity<ApiUtils.ApiResult<GetPendingArtistListResponseDTO>> getPendingArtistList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam String status
    ) {

        return ResponseEntity.ok(
                ApiUtils.success(pendingArtistQueryUseCase.getPendingArtists(page, size, ApproveStatus.from(status))));
    }

    // 신청 처리 대기 아티스트 리스트 조회 API
    @GetMapping("/applications/artists/{artistId}")
    public ResponseEntity<ApiUtils.ApiResult<GetPendingArtistDetailResponseDTO>> getPendingArtistDetail(
            @PathVariable Long artistId) {
        return ResponseEntity.ok(ApiUtils.success(pendingArtistQueryUseCase.getPendingArtistDetail(artistId)));
    }

    // 아티스트 신청 승인 API
    @PostMapping("/applications/artists/{artistId}/approve")
    public ResponseEntity<ApiResult<?>> approveArtist(@PathVariable Long artistId,
                                                      @RequestBody ApproveArtistRequestDTO request) {
        return ResponseEntity.ok(ApiUtils.success());
    }

}
