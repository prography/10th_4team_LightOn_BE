package com.prography.lighton.artist.admin.application.impl;

import com.prography.lighton.artist.admin.application.ManageArtistApplicationUseCase;
import com.prography.lighton.artist.admin.presentation.dto.request.ManageArtistApplicationRequestDTO;
import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.users.infrastructure.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ManageArtistApplicationUseCaseImpl implements ManageArtistApplicationUseCase {

    private final ArtistRepository artistRepository;

    @Override
    public void manageArtistApplication(Long artistId, ManageArtistApplicationRequestDTO requestDTO) {
        Artist artist = artistRepository.getById(artistId);
        ApproveStatus approveStatus = ApproveStatus.from(requestDTO.status());
        artist.manageArtistApplication(approveStatus);
    }
}
