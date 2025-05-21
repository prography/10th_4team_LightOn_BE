package com.prography.lighton.member.application.admin;

import static com.prography.lighton.artist.domain.entity.enums.ApproveStatus.APPROVED;

import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.infrastructure.repository.ArtistRepository;
import com.prography.lighton.member.application.admin.mapper.PendingArtistMapper;
import com.prography.lighton.member.presentation.dto.response.GetArtistApplicationDetailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.GetArtistApplicationListResponseDTO;
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
    public GetArtistApplicationListResponseDTO getAllPendingArtists(int page, int size) {
        return getArtists(PageRequest.of(page, size), Optional.empty());
    }

    @Override
    public GetArtistApplicationListResponseDTO getPendingArtistsByApproveStatus(int page, int size,
                                                                                ApproveStatus approveStatus) {
        return getArtists(PageRequest.of(page, size), Optional.of(approveStatus));
    }

    private GetArtistApplicationListResponseDTO getArtists(Pageable pageable, Optional<ApproveStatus> optionalStatus) {
        Page<Artist> artists = optionalStatus
                .map(status -> artistRepository.findByApproveStatus(status, pageable))
                .orElseGet(() -> artistRepository.findUnapprovedArtists(APPROVED, pageable));

        var dtoPage = artists.map(PendingArtistMapper::toPendingArtistDTO);
        return GetArtistApplicationListResponseDTO.of(dtoPage);
    }

    @Override
    public GetArtistApplicationDetailResponseDTO getPendingArtistDetail(Long artistId) {
        Artist artist = artistRepository.getById(artistId);

        return PendingArtistMapper.toPendingArtistDetailResponseDTO(artist);
    }
}
