package com.prography.lighton.member.application.admin;

import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistDetailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistListResponseDTO;

public interface PendingArtistQueryUseCase {

    GetPendingArtistListResponseDTO getAllPendingArtists(int page, int size);

    GetPendingArtistListResponseDTO getPendingArtistsByApproveStatus(int page, int size, ApproveStatus approveStatus);

    GetPendingArtistDetailResponseDTO getPendingArtistDetail(Long artistId);
}
