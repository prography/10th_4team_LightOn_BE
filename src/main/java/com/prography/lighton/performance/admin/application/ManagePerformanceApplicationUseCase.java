package com.prography.lighton.performance.admin.application;

import com.prography.lighton.performance.admin.presentation.dto.request.ManagePerformanceApplicationRequestDTO;

public interface ManagePerformanceApplicationUseCase {

    void manageArtistApplication(Long performanceId, ManagePerformanceApplicationRequestDTO requestDTO);
}
