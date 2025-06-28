package com.prography.lighton.performance.users.presentation.dto.response;

import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.users.presentation.dto.response.enums.PerformanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record GetMyRegisteredPerformanceListResponseDTO(
        List<RegisteredPerformanceResponseDTO> performanceList
) {

    public static GetMyRegisteredPerformanceListResponseDTO from(
            List<Performance> performanceList
    ) {
        return new GetMyRegisteredPerformanceListResponseDTO(
                performanceList.stream()
                        .map(performance -> RegisteredPerformanceResponseDTO.from(
                                performance.getId(),
                                performance.getInfo().getTitle(),
                                performance.getSchedule().getStartDate(),
                                performance.getSchedule().getStartTime(),
                                PerformanceStatus.getPerformanceStatus(
                                        performance.getApproveStatus(),
                                        performance.getSchedule().getEndDate(),
                                        performance.getSchedule().getEndTime()
                                ),
                                performance.getInfo().getPlace(),
                                performance.getPayment().getIsPaid(),
                                performance.getCreatedAt().toLocalDate()
                        ))
                        .toList()
        );
    }

    private record RegisteredPerformanceResponseDTO(
            Long id,
            String title,
            LocalDate startDate,
            LocalTime startTime,
            PerformanceStatus performanceStatus,
            String address,
            boolean isPaid,
            LocalDate createdAt
    ) {
        public static RegisteredPerformanceResponseDTO from(
                Long id,
                String title,
                LocalDate startDate,
                LocalTime startTime,
                PerformanceStatus performanceStatus,
                String address,
                boolean isPaid,
                LocalDate createdAt
        ) {
            return new RegisteredPerformanceResponseDTO(
                    id, title, startDate, startTime, performanceStatus, address, isPaid, createdAt
            );
        }
    }
}
