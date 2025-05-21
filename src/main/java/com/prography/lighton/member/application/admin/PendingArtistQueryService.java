package com.prography.lighton.member.application.admin;

import static com.prography.lighton.artist.domain.entity.enums.ApproveStatus.PENDING;

import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.infrastructure.repository.ArtistRepository;
import com.prography.lighton.member.application.admin.mapper.PendingArtistMapper;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistDetailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PendingArtistQueryService implements PendingArtistQueryUseCase {

    private final ArtistRepository artistRepository;

    @Override
    public GetPendingArtistListResponseDTO getPendingArtists(int page, int size) {
        return null;
    }

    @Override
    public GetPendingArtistDetailResponseDTO getPendingArtistDetail(Long artistId) {
        Artist artist = artistRepository.getByIdAndApproveStatus(artistId, PENDING);
        return PendingArtistMapper.toPendingArtistDetailResponseDTO(artist);
    }
}
