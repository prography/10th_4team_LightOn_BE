package com.prography.lighton.member.admin.application;

import com.prography.lighton.member.admin.presentation.ManageArtistApplicationRequestDTO;

public interface ManageArtistApplicationUseCase {

    void manageArtistApplication(Long artistId, ManageArtistApplicationRequestDTO requestDTO);
}
