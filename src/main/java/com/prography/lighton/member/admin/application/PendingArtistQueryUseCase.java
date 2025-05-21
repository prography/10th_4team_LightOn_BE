package com.prography.lighton.member.admin.application;

import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.member.admin.presentation.GetArtistApplicationDetailResponseDTO;
import com.prography.lighton.member.admin.presentation.GetArtistApplicationListResponseDTO;

public interface PendingArtistQueryUseCase {

    GetArtistApplicationListResponseDTO getAllPendingArtists(int page, int size);

    GetArtistApplicationListResponseDTO getPendingArtistsByApproveStatus(int page, int size,
                                                                         ApproveStatus approveStatus);

    GetArtistApplicationDetailResponseDTO getPendingArtistDetail(Long artistId);
}
