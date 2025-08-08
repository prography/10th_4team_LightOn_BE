package com.prography.lighton.performance.common.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import com.prography.lighton.performance.common.domain.exception.InvalidScheduleException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScheduleTest {

    @DisplayName("Schedule 객체를 정상적으로 생성할 수 있다.")
    @Test
    void should_create_schedule() {
        LocalDate startDate = LocalDate.of(2025, 8, 1);
        LocalDate endDate = LocalDate.of(2025, 8, 2);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        Schedule schedule = Schedule.of(startDate, endDate, startTime, endTime);

        assertEquals(startDate, schedule.getStartDate());
        assertEquals(endDate, schedule.getEndDate());
        assertEquals(startTime, schedule.getStartTime());
        assertEquals(endTime, schedule.getEndTime());
    }

    @DisplayName("Schedule 객체 생성 시 종료일보다 생성일이 빠르면 예외가 발생한다.")
    @Test
    void should_throw_exception_when_endDate_before_startDate() {
        LocalDate startDate = LocalDate.of(2025, 8, 5);
        LocalDate endDate = LocalDate.of(2025, 8, 4);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        assertThrows(InvalidScheduleException.class, () ->
                Schedule.of(startDate, endDate, startTime, endTime)
        );
    }

    @DisplayName("Schedule 객체 생성 시 종료 시간이 생성 시간보다 빠르면 예외가 발생한다.")
    @Test
    void should_throw_exception_when_endTime_before_startTime() {
        LocalDate startDate = LocalDate.of(2025, 8, 1);
        LocalDate endDate = LocalDate.of(2025, 8, 2);
        LocalTime startTime = LocalTime.of(15, 0);
        LocalTime endTime = LocalTime.of(14, 0);

        assertThrows(InvalidScheduleException.class, () ->
                Schedule.of(startDate, endDate, startTime, endTime)
        );
    }
}
