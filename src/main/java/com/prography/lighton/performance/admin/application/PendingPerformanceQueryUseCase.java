package com.prography.lighton.performance.admin.application;

import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationDetailResponseDTO;
import com.prography.lighton.performance.admin.presentation.dto.response.GetPerformanceApplicationListResponseDTO;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import java.util.List;

public interface PendingPerformanceQueryUseCase {

    GetPerformanceApplicationListResponseDTO getAllPerformanceApplications(int page, int size,
                                                                           List<ApproveStatus> approveStatuses);

    GetPerformanceApplicationDetailResponseDTO getPendingPerformanceDetail(Long performanceId);
}
