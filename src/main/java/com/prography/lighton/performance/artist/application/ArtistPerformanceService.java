package com.prography.lighton.performance.artist.application;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.artist.presentation.dto.response.GetPerformanceRequestsResponseDTO;
import com.prography.lighton.performance.common.domain.entity.enums.RequestStatus;

public interface ArtistPerformanceService {

    GetPerformanceRequestsResponseDTO getPerformanceRequests(Long performanceId, Member member);

    void managePerformanceRequest(Long performanceId, Long applicantId, Member member, RequestStatus requestStatus);
}
