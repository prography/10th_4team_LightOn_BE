package com.prography.lighton.artist.admin.application;

import com.prography.lighton.artist.admin.presentation.ManageArtistApplicationRequestDTO;

public interface ManageArtistApplicationUseCase {

    void manageArtistApplication(Long artistId, ManageArtistApplicationRequestDTO requestDTO);
}
