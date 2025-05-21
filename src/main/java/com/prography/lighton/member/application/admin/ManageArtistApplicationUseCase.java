package com.prography.lighton.member.application.admin;

import com.prography.lighton.member.presentation.dto.request.ManageArtistApplicationRequestDTO;

public interface ManageArtistApplicationUseCase {

    void manageArtistApplication(Long artistId, ManageArtistApplicationRequestDTO requestDTO);
}
