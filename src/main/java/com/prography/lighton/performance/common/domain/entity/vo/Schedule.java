package com.prography.lighton.performance.common.domain.entity.vo;

import com.prography.lighton.performance.common.domain.exception.InvalidScheduleException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Schedule {

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    public static Schedule of(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        isValidSchedule(startDate, endDate, startTime, endTime);
        return new Schedule(startDate, endDate, startTime, endTime);
    }

    private static void isValidSchedule(LocalDate startDate, LocalDate endDate, LocalTime startTime,
                                        LocalTime endTime) {
        if (endDate.isBefore(startDate)) {
            throw new InvalidScheduleException("종료일은 시작일보다 빠를 수 없습니다.");
        }

        if (endTime.isBefore(startTime)) {
            throw new InvalidScheduleException("종료 시간은 시작 시간보다 뒤여야 합니다.");
        }
    }
}
