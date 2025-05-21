package com.prography.lighton.artist.admin.application;

import com.prography.lighton.artist.admin.presentation.GetArtistApplicationDetailResponseDTO;
import com.prography.lighton.artist.admin.presentation.GetArtistApplicationListResponseDTO;
import com.prography.lighton.artist.users.domain.entity.enums.ApproveStatus;

public interface ArtistApplicationQueryUseCase {

    GetArtistApplicationListResponseDTO getAllArtistApplications(int page, int size);

    GetArtistApplicationListResponseDTO getArtistApplicationsByApproveStatus(int page, int size,
                                                                             ApproveStatus approveStatus);

    GetArtistApplicationDetailResponseDTO getPendingArtistDetail(Long artistId);
}
