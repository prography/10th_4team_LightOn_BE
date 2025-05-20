package com.prography.lighton.member.application.admin;

import com.prography.lighton.member.presentation.dto.response.GetPendingArtistDetailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistListResponseDTO;

public interface PendingArtistQueryUseCase {

    GetPendingArtistListResponseDTO getPendingArtists(int page, int size);

    GetPendingArtistDetailResponseDTO getPendingArtistDetail(Long artistId);
}
