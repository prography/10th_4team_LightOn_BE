package com.prography.lighton.artist.admin.presentation;

import com.prography.lighton.artist.admin.application.ArtistApplicationQueryUseCase;
import com.prography.lighton.artist.admin.application.GetArtistStatsUseCase;
import com.prography.lighton.artist.admin.application.ManageArtistApplicationUseCase;
import com.prography.lighton.artist.admin.presentation.dto.request.ManageArtistApplicationRequestDTO;
import com.prography.lighton.artist.admin.presentation.dto.response.GetArtistApplicationDetailResponseDTO;
import com.prography.lighton.artist.admin.presentation.dto.response.GetArtistApplicationListResponseDTO;
import com.prography.lighton.artist.admin.presentation.dto.response.GetArtistStatsResponseDTO;
import com.prography.lighton.artist.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.common.annotation.AdminOnly;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
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
@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminArtistController {

    private final ArtistApplicationQueryUseCase artistApplicationQueryUseCase;
    private final ManageArtistApplicationUseCase manageArtistApplicationUseCase;
    private final GetArtistStatsUseCase getArtistStatsUseCase;

    @GetMapping("/applications/artists")
    public ResponseEntity<ApiResult<GetArtistApplicationListResponseDTO>> getArtistApplicationList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<ApproveStatus> statuses
    ) {
        GetArtistApplicationListResponseDTO result =
                artistApplicationQueryUseCase.getAllArtistApplications(page, size, statuses);

        return ResponseEntity.ok(ApiUtils.success(result));
    }

    @GetMapping("/applications/artists/{artistId}")
    public ResponseEntity<ApiUtils.ApiResult<GetArtistApplicationDetailResponseDTO>> getArtistApplicationDetail(
            @PathVariable Long artistId) {
        return ResponseEntity.ok(ApiUtils.success(
                artistApplicationQueryUseCase.getPendingArtistDetail(artistId)));
    }

    @PostMapping("/applications/artists/{artistId}/approve")
    public ResponseEntity<ApiResult<?>> manageArtistApplication(@PathVariable Long artistId,
                                                                @RequestBody ManageArtistApplicationRequestDTO request) {
        manageArtistApplicationUseCase.manageArtistApplication(artistId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @GetMapping("/artists/stats")
    public ResponseEntity<ApiResult<GetArtistStatsResponseDTO>> getArtistStats() {
        return ResponseEntity.ok(ApiUtils.success(getArtistStatsUseCase.getArtistStats()));
    }

}
