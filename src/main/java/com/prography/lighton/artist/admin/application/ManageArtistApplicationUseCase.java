package com.prography.lighton.artist.admin.application;

import com.prography.lighton.artist.admin.presentation.dto.request.ManageArtistApplicationRequestDTO;

public interface ManageArtistApplicationUseCase {

    void manageArtistApplication(Long artistId, ManageArtistApplicationRequestDTO requestDTO);
}
