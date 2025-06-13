package com.prography.lighton.performance.admin.application;

import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceStatsResponseDTO;

public interface GetPerformanceStatsUseCase {

    GetPerformanceStatsResponseDTO getPerformanceStats();
}
