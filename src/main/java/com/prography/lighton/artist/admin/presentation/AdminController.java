package com.prography.lighton.artist.admin.presentation;

import com.prography.lighton.artist.admin.application.ArtistApplicationQueryUseCase;
import com.prography.lighton.artist.admin.application.ManageArtistApplicationUseCase;
import com.prography.lighton.artist.users.domain.entity.enums.ApproveStatus;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final ArtistApplicationQueryUseCase artistApplicationQueryUseCase;
    private final ManageArtistApplicationUseCase manageArtistApplicationUseCase;

    @GetMapping("/applications/artists")
    public ResponseEntity<ApiResult<GetArtistApplicationListResponseDTO>> getArtistApplicationList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status
    ) {
        GetArtistApplicationListResponseDTO result;

        if (StringUtils.isBlank(status)) {
            result = artistApplicationQueryUseCase.getAllArtistApplications(page, size);
        } else {
            ApproveStatus approveStatus = ApproveStatus.from(status);
            result = artistApplicationQueryUseCase.getArtistApplicationsByApproveStatus(page, size, approveStatus);
        }

        return ResponseEntity.ok(ApiUtils.success(result));
    }

    @GetMapping("/applications/artists/{artistId}")
    public ResponseEntity<ApiUtils.ApiResult<GetArtistApplicationDetailResponseDTO>> getArtistApplicationDetail(
            @PathVariable Long artistId) {
        return ResponseEntity.ok(ApiUtils.success(
                artistApplicationQueryUseCase.getPendingArtistDetail(artistId)));
    }

    // 아티스트 신청 승인 API
    @PostMapping("/applications/artists/{artistId}/approve")
    public ResponseEntity<ApiResult<?>> manageArtistApplication(@PathVariable Long artistId,
                                                                @RequestBody ManageArtistApplicationRequestDTO request) {
        manageArtistApplicationUseCase.manageArtistApplication(artistId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }

}
