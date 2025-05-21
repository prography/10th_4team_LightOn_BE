package com.prography.lighton.member.application.admin;

import com.prography.lighton.artist.application.exception.NoSuchArtistException;
import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.infrastructure.repository.ArtistRepository;
import com.prography.lighton.member.presentation.dto.request.ManageArtistApplicationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ManageArtistApplicationService implements ManageArtistApplicationUseCase {

    private final ArtistRepository artistRepository;

    @Override
    public void manageArtistApplication(Long artistId, ManageArtistApplicationRequestDTO requestDTO) {
        Artist artist = artistRepository.findByIdAndApproveStatus(artistId, ApproveStatus.PENDING)
                .orElseThrow(() -> new NoSuchArtistException("해당 아티스트는 이미 처리 되었거나 존재하지 않습니다."));
        ApproveStatus approveStatus = ApproveStatus.from(requestDTO.status());
        artist.manageArtistApplication(approveStatus);
    }
}
