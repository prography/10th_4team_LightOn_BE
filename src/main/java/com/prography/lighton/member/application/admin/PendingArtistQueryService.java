package com.prography.lighton.member.application.admin;

import static com.prography.lighton.artist.domain.entity.enums.ApproveStatus.APPROVED;
import static com.prography.lighton.artist.domain.entity.enums.ApproveStatus.PENDING;

import com.prography.lighton.artist.application.exception.NoSuchArtistException;
import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.infrastructure.repository.ArtistRepository;
import com.prography.lighton.member.application.admin.mapper.PendingArtistMapper;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistDetailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetPendingArtistListResponseDTO;
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
public class PendingArtistQueryService implements PendingArtistQueryUseCase {

    private final ArtistRepository artistRepository;

    @Override
    public GetPendingArtistListResponseDTO getAllPendingArtists(int page, int size) {
        return getArtists(PageRequest.of(page, size), Optional.empty());
    }

    @Override
    public GetPendingArtistListResponseDTO getPendingArtistsByApproveStatus(int page, int size,
                                                                            ApproveStatus approveStatus) {
        return getArtists(PageRequest.of(page, size), Optional.of(approveStatus));
    }

    private GetPendingArtistListResponseDTO getArtists(Pageable pageable, Optional<ApproveStatus> optionalStatus) {
        Page<Artist> artists = optionalStatus
                .map(status -> artistRepository.findByApproveStatus(status, pageable))
                .orElseGet(() -> artistRepository.findByApproveStatusNotApproved(APPROVED, pageable));

        var dtoPage = artists.map(PendingArtistMapper::toPendingArtistDTO);
        return GetPendingArtistListResponseDTO.of(dtoPage);
    }

    @Override
    public GetPendingArtistDetailResponseDTO getPendingArtistDetail(Long artistId) {
        Artist artist = artistRepository.findByIdAndApproveStatus(artistId, PENDING)
                .orElseThrow(() -> new NoSuchArtistException("해당 아티스트는 이미 처리 되었거나 존재하지 않습니다."));

        return PendingArtistMapper.toPendingArtistDetailResponseDTO(artist);
    }
}
