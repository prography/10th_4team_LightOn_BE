package com.prography.lighton.member.presentation;

import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.application.admin.ManageArtistApplicationUseCase;
import com.prography.lighton.member.application.admin.PendingArtistQueryUseCase;
import com.prography.lighton.member.presentation.dto.request.ManageArtistApplicationRequestDTO;
import com.prography.lighton.member.presentation.dto.response.GetArtistApplicationDetailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetArtistApplicationListResponseDTO;
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

    private final PendingArtistQueryUseCase pendingArtistQueryUseCase;
    private final ManageArtistApplicationUseCase manageArtistApplicationUseCase;

    @GetMapping("/applications/artists")
    public ResponseEntity<ApiResult<GetArtistApplicationListResponseDTO>> getArtistApplicationList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status
    ) {
        GetArtistApplicationListResponseDTO result =
                StringUtils.isBlank(status)
                        ? pendingArtistQueryUseCase.getAllPendingArtists(page, size)
                        : pendingArtistQueryUseCase.getPendingArtistsByApproveStatus(page, size,
                                ApproveStatus.from(status));

        return ResponseEntity.ok(ApiUtils.success(result));
    }

    @GetMapping("/applications/artists/{artistId}")
    public ResponseEntity<ApiUtils.ApiResult<GetArtistApplicationDetailResponseDTO>> getArtistApplicationList(
            @PathVariable Long artistId) {
        return ResponseEntity.ok(ApiUtils.success(
                pendingArtistQueryUseCase.getPendingArtistDetail(artistId)));
    }

    // 아티스트 신청 승인 API
    @PostMapping("/applications/artists/{artistId}/approve")
    public ResponseEntity<ApiResult<?>> manageArtistApplication(@PathVariable Long artistId,
                                                                @RequestBody ManageArtistApplicationRequestDTO request) {
        manageArtistApplicationUseCase.manageArtistApplication(artistId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }

}
