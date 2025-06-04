package com.prography.lighton.artist.admin.application.impl;

import com.prography.lighton.artist.admin.application.GetArtistStatsUseCase;
import com.prography.lighton.artist.admin.infrastructure.repository.AdminArtistRepository;
import com.prography.lighton.artist.admin.presentation.dto.response.GetArtistStatsResponseDTO;
import com.prography.lighton.artist.common.domain.entity.enums.ApproveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetArtistStatsUseCaseImpl implements GetArtistStatsUseCase {

    private final AdminArtistRepository adminArtistRepository;

    @Override
    public GetArtistStatsResponseDTO getArtistStats() {
        return GetArtistStatsResponseDTO.of(
                adminArtistRepository.countByApproveStatus(ApproveStatus.APPROVED)
        );
    }
}
