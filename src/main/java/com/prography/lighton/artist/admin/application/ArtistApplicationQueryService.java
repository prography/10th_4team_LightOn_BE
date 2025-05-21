package com.prography.lighton.artist.admin.application;

import static com.prography.lighton.artist.admin.domain.enums.ApproveStatus.APPROVED;

import com.prography.lighton.artist.admin.application.mapper.PendingArtistMapper;
import com.prography.lighton.artist.admin.domain.enums.ApproveStatus;
import com.prography.lighton.artist.admin.infrastructure.repository.AdminArtistRepository;
import com.prography.lighton.artist.admin.presentation.GetArtistApplicationDetailResponseDTO;
import com.prography.lighton.artist.admin.presentation.GetArtistApplicationListResponseDTO;
import com.prography.lighton.artist.users.application.exception.NoSuchArtistException;
import com.prography.lighton.artist.users.domain.entity.Artist;
import com.prography.lighton.artist.users.infrastructure.repository.ArtistRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistApplicationQueryService implements ArtistApplicationQueryUseCase {

    private final ArtistRepository artistRepository;
    private final AdminArtistRepository adminArtistRepository;

    @Override
    public GetArtistApplicationListResponseDTO getAllArtistApplications(int page, int size) {
        return getArtists(PageRequest.of(page, size), Optional.empty());
    }

    @Override
    public GetArtistApplicationListResponseDTO getArtistApplicationsByApproveStatus(int page, int size,
                                                                                    ApproveStatus approveStatus) {
        return getArtists(PageRequest.of(page, size), Optional.of(approveStatus));
    }

    private GetArtistApplicationListResponseDTO getArtists(Pageable pageable, Optional<ApproveStatus> optionalStatus) {
        Page<Artist> artists = optionalStatus
                .map(status -> adminArtistRepository.findByApproveStatus(status, pageable))
                .orElseGet(() -> adminArtistRepository.findUnapprovedArtists(APPROVED, pageable));

        var dtoPage = artists.map(PendingArtistMapper::toPendingArtistDTO);
        return GetArtistApplicationListResponseDTO.of(dtoPage);
    }

    @Override
    public GetArtistApplicationDetailResponseDTO getPendingArtistDetail(Long artistId) {
        Artist artist = adminArtistRepository.findByIdAndApproveStatus(artistId, ApproveStatus.PENDING)
                .orElseThrow(() -> new NoSuchArtistException("해당 아티스트는 이미 처리 되었거나 존재하지 않습니다."));

        return PendingArtistMapper.toPendingArtistDetailResponseDTO(artist);
    }
}
