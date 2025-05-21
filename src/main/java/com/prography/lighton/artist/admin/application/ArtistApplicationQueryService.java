package com.prography.lighton.artist.admin.application;

import static com.prography.lighton.artist.common.domain.entity.enums.ApproveStatus.PENDING;
import static com.prography.lighton.artist.common.domain.entity.enums.ApproveStatus.REJECTED;

import com.prography.lighton.artist.admin.application.mapper.PendingArtistMapper;
import com.prography.lighton.artist.admin.infrastructure.repository.AdminArtistRepository;
import com.prography.lighton.artist.admin.presentation.dto.response.GetArtistApplicationDetailResponseDTO;
import com.prography.lighton.artist.admin.presentation.dto.response.GetArtistApplicationListResponseDTO;
import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.users.application.exception.NoSuchArtistException;
import java.util.List;
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

    private final AdminArtistRepository adminArtistRepository;
    private final PendingArtistMapper pendingArtistMapper;

    @Override
    public GetArtistApplicationListResponseDTO getAllArtistApplications(int page, int size,
                                                                        List<ApproveStatus> statuses) {
        Pageable pageable = PageRequest.of(page, size);

        List<ApproveStatus> effectiveStatuses;
        if (statuses == null || statuses.isEmpty()) {
            effectiveStatuses = List.of(PENDING, REJECTED); // 기본값
        } else {
            effectiveStatuses = statuses;
        }

        Page<Artist> artists = adminArtistRepository.findByApproveStatuses(effectiveStatuses, pageable);
        var dtoPage = artists.map(pendingArtistMapper::toPendingArtistDTO);

        return GetArtistApplicationListResponseDTO.of(dtoPage);
    }

    @Override
    public GetArtistApplicationDetailResponseDTO getPendingArtistDetail(Long artistId) {
        Artist artist = adminArtistRepository.findByIdAndApproveStatus(artistId, PENDING)
                .orElseThrow(() -> new NoSuchArtistException("해당 아티스트는 이미 처리 되었거나 존재하지 않습니다."));

        return pendingArtistMapper.toPendingArtistDetailResponseDTO(artist);
    }
}
