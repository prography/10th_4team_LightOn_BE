package com.prography.lighton.performance.admin.application;

import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationListResponseDTO;
import com.prography.lighton.performance.users.domain.entity.enums.ApproveStatus;

public interface PendingPerformanceQueryUseCase {

    GetPerformanceApplicationListResponseDTO getAllPendingArtists(int page, int size);

    GetPerformanceApplicationListResponseDTO getPendingArtistsByApproveStatus(int page, int size,
                                                                              ApproveStatus approveStatus);

    GetPerformanceApplicationDetailResponseDTO getPendingArtistDetail(Long artistId);
}
