package com.prography.lighton.performance.artist.presentation.dto.response;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import com.prography.lighton.performance.common.domain.entity.enums.RequestStatus;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import java.util.List;

public record GetPerformanceRequestsResponseDTO(
        Long id,
        Info info,
        List<String> genres,
        Schedule schedule,
        List<PerformanceRequestDTO> performanceApplications
) {

    public static GetPerformanceRequestsResponseDTO of(Long id, Info info, List<String> genres,
                                                       Schedule schedule,
                                                       List<PerformanceRequest> performanceRequests) {
        List<PerformanceRequestDTO> performanceRequestDTOS = performanceRequests.stream()
                .map(request -> PerformanceRequestDTO.of(
                        request.getId(),
                        request.getMember().getId(),
                        request.getMember().getName(),
                        request.getMember().getEmail().getValue(),
                        request.getRequestStatus()
                ))
                .toList();
        return new GetPerformanceRequestsResponseDTO(id, info, genres, schedule, performanceRequestDTOS);
    }

    private record PerformanceRequestDTO(
            Long requestId,
            Long applicantId,
            String applicantName,
            String applicantEmail,
            RequestStatus requestStatus
    ) {
        public static PerformanceRequestDTO of(Long requestId, Long applicantId,String applicantName, String applicantEmail,
                                               RequestStatus requestStatus) {
            return new PerformanceRequestDTO(requestId, applicantId, applicantName, applicantEmail, requestStatus);
        }
    }
}
