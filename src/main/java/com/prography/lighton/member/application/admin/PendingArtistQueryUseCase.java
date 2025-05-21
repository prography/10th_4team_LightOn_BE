package com.prography.lighton.member.application.admin;

import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.member.presentation.dto.response.GetArtisApplicationListResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetArtistApplicationDetailResponseDTO;

public interface PendingArtistQueryUseCase {

    GetArtisApplicationListResponseDTO getAllPendingArtists(int page, int size);

    GetArtisApplicationListResponseDTO getPendingArtistsByApproveStatus(int page, int size,
                                                                        ApproveStatus approveStatus);

    GetArtistApplicationDetailResponseDTO getPendingArtistDetail(Long artistId);
}
