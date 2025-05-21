package com.prography.lighton.artist.admin.application;

import com.prography.lighton.artist.admin.domain.enums.ApproveStatus;
import com.prography.lighton.artist.admin.infrastructure.repository.AdminArtistRepository;
import com.prography.lighton.artist.admin.presentation.ManageArtistApplicationRequestDTO;
import com.prography.lighton.artist.users.application.exception.NoSuchArtistException;
import com.prography.lighton.artist.users.domain.entity.Artist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ManageArtistApplicationService implements ManageArtistApplicationUseCase {

    private final AdminArtistRepository adminArtistRepository;

    @Override
    public void manageArtistApplication(Long artistId, ManageArtistApplicationRequestDTO requestDTO) {
        Artist artist = adminArtistRepository.findByIdAndApproveStatus(artistId, ApproveStatus.PENDING)
                .orElseThrow(() -> new NoSuchArtistException("해당 아티스트는 이미 처리 되었거나 존재하지 않습니다."));
        ApproveStatus approveStatus = ApproveStatus.from(requestDTO.status());
        artist.manageArtistApplication(approveStatus);
    }
}
