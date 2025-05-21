package com.prography.lighton.member.application.admin;

import static com.prography.lighton.artist.domain.entity.enums.ApproveStatus.PENDING;

import com.prography.lighton.artist.application.exception.NoSuchArtistException;
import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.infrastructure.repository.ArtistRepository;
import com.prography.lighton.member.application.admin.mapper.PendingArtistMapper;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistDetailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PendingArtistQueryService implements PendingArtistQueryUseCase {

    private final ArtistRepository artistRepository;

    @Override
    public GetPendingArtistListResponseDTO getPendingArtists(int page, int size, ApproveStatus approveStatus) {
        var pendingArtists = artistRepository.findByApproveStatus(approveStatus, PageRequest.of(page, size));

        var pendingArtistDTOs = pendingArtists.map(PendingArtistMapper::toPendingArtistDTO);

        return GetPendingArtistListResponseDTO.of(pendingArtistDTOs);
    }

    @Override
    public GetPendingArtistDetailResponseDTO getPendingArtistDetail(Long artistId) {
        Artist artist = artistRepository.findByIdAndApproveStatus(artistId, PENDING)
                .orElseThrow(() -> new NoSuchArtistException("해당 아티스트는 이미 처리 되었거나 존재하지 않습니다."));

        return PendingArtistMapper.toPendingArtistDetailResponseDTO(artist);
    }
}
