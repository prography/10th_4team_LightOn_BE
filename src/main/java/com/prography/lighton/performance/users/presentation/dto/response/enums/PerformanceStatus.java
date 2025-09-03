package com.prography.lighton.performance.users.presentation.dto.response.enums;

import com.prography.lighton.common.exception.base.UnsupportedTypeException;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum PerformanceStatus {
    PENDING, APPROVED, FINISHED, REJECTED, CANCELED;

    public static PerformanceStatus getPerformanceStatus(
            ApproveStatus approveStatus, LocalDate finishDate, LocalTime finishTime) {

        LocalDateTime currentDateTime = LocalDateTime.now();

        if (currentDateTime.toLocalDate().isAfter(finishDate) ||
                (currentDateTime.toLocalDate().isEqual(finishDate) &&
                        currentDateTime.toLocalTime().isAfter(finishTime))) {
            return PerformanceStatus.FINISHED;
        }

        if (approveStatus == ApproveStatus.PENDING) {
            return PerformanceStatus.PENDING;
        } else if (approveStatus == ApproveStatus.APPROVED) {
            return PerformanceStatus.APPROVED;
        } else if (approveStatus == ApproveStatus.REJECTED) {
            return PerformanceStatus.REJECTED;
        } else if (approveStatus == ApproveStatus.CANCELED) {
            return PerformanceStatus.CANCELED;
        }

        throw new UnsupportedTypeException("지원하지 않는 공연 상태입니다.");
    }
}
