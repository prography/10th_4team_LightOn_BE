package com.prography.lighton.performance.admin.application;

import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationsListResponseDTO;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.presentation.dto.response.GetPerformanceDetailResponseDTO;
import java.util.List;

public interface PerformanceApplicationQueryUseCase {

    GetPerformanceApplicationsListResponseDTO getAllPerformanceApplications(int page, int size, Type type,
                                                                            List<ApproveStatus> approveStatuses);

    GetPerformanceDetailResponseDTO getPendingPerformanceDetail(Long performanceId);
}
