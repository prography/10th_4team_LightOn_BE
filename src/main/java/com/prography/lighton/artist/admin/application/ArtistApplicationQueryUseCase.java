package com.prography.lighton.artist.admin.application;

import com.prography.lighton.artist.admin.presentation.GetArtistApplicationDetailResponseDTO;
import com.prography.lighton.artist.admin.presentation.GetArtistApplicationListResponseDTO;
import com.prography.lighton.artist.common.domain.entity.enums.ApproveStatus;
import java.util.List;

public interface ArtistApplicationQueryUseCase {

    GetArtistApplicationListResponseDTO getAllArtistApplications(int page, int size, List<ApproveStatus> statuses);

    GetArtistApplicationDetailResponseDTO getPendingArtistDetail(Long artistId);
}
