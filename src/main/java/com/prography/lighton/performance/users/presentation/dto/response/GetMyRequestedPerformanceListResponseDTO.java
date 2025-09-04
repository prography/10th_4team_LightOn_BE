package com.prography.lighton.performance.users.presentation.dto.response;

import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.users.presentation.dto.response.enums.PerformanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record GetMyRequestedPerformanceListResponseDTO(List<RequestedPerformanceResponseDTO> performanceList) {

    public static GetMyRequestedPerformanceListResponseDTO from(
            List<PerformanceRequest> performanceList
    ) {
        return new GetMyRequestedPerformanceListResponseDTO(
                performanceList.stream()
                        .map(request -> RequestedPerformanceResponseDTO.from(
                                request.getPerformance().getId(),
                                request.getPerformance().getInfo().getTitle(),
                                request.getPerformance().getSchedule().getStartDate(),
                                request.getPerformance().getSchedule().getStartTime(),
                                PerformanceStatus.getPerformanceStatus(
                                        request.getPerformance().getApproveStatus(),
                                        request.getPerformance().getSchedule().getEndDate(),
                                        request.getPerformance().getSchedule().getEndTime()
                                ),
                                request.getPerformance().getInfo().getPlace(),
                                request.getPerformance().getPayment().getIsPaid(),
                                request.getRequestedAt().toLocalDate(),
                                request.getPerformance().getType()
                        ))
                        .toList()
        );
    }

    private record RequestedPerformanceResponseDTO(
            Long id,
            String title,
            LocalDate startDate,
            LocalTime startTime,
            PerformanceStatus performanceStatus,
            String address,
            boolean isPaid,
            LocalDate requestedAt,
            boolean isConcert
    ) {
        public static RequestedPerformanceResponseDTO from(
                Long id,
                String title,
                LocalDate startDate,
                LocalTime startTime,
                PerformanceStatus performanceStatus,
                String address,
                boolean isPaid,
                LocalDate requestedAt,
                Type type

        ) {
            return new RequestedPerformanceResponseDTO(
                    id, title, startDate, startTime, performanceStatus, address, isPaid, requestedAt,
                    type == Type.CONCERT
            );
        }
    }
}
