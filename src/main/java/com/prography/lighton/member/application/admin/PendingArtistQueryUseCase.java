package com.prography.lighton.member.application.admin;

import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.member.presentation.dto.response.GetArtistApplicationDetailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetArtistApplicationListResponseDTO;

public interface PendingArtistQueryUseCase {

    GetArtistApplicationListResponseDTO getAllPendingArtists(int page, int size);

    GetArtistApplicationListResponseDTO getPendingArtistsByApproveStatus(int page, int size,
                                                                         ApproveStatus approveStatus);

    GetArtistApplicationDetailResponseDTO getPendingArtistDetail(Long artistId);
}
